package com.ysxsoft.deliverylocker.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class UpdataAPP {

    public Context context;
    private DownloadApkAsyncTask mAsyncTask = null;

    public UpdataAPP(Context context) {
        this.context = context;
    }

    /**
     * 版本更新----起
     *  <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
     *  <--8.0 安装权限-->
     */
// 更新版本要用到的一些信息
    public void updateAPP(String url) {
        mAsyncTask = new DownloadApkAsyncTask();
        mAsyncTask.execute(url);
    }


    class DownloadApkAsyncTask extends AsyncTask<String, Integer, Integer> {

        private static final int DOWNLOAD_OK = 0x00;
        private static final int DOWNLOAD_ERROR = 0x01;

        private File apkFile = null;
        private File updateDir = null;

        public DownloadApkAsyncTask() {
            super();
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setConnectTimeout(15000);
                InputStream in = connection.getInputStream();
                int curSize = 0;
                int progress = 0;
                int totalSize = connection.getContentLength();
                if (Environment.MEDIA_MOUNTED
                        .equals(Environment
                                .getExternalStorageState())) {
                    updateDir = new File(Environment
                            .getExternalStorageDirectory().getPath()
                            + "/townnet/apk/");
                } else {
                    updateDir = new File("/data/data/" + context.getPackageName()
                            + "/apk/");
                }
                if (!updateDir.exists()) {
                    updateDir.mkdirs();
                }
                apkFile = new File(updateDir.getPath(), "hongyun.apk");
                if (apkFile.exists()) {
                    apkFile.delete();
                }
                // 修改文件夹及安装包的权限,供第三方应用访问
                try {
                    Runtime.getRuntime().exec(
                            "chmod 705 " + updateDir.getPath());
                    Runtime.getRuntime().exec("chmod 604 " + apkFile.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int temp;
                byte[] bytes = new byte[1024];
                FileOutputStream out = new FileOutputStream(apkFile);
                while ((temp = in.read(bytes)) != -1) {
                    out.write(bytes, 0, temp);
                    curSize += temp;
                    progress = curSize * 100 / totalSize;
                    publishProgress(curSize);
                }
                in.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                return DOWNLOAD_ERROR;
            }
            return DOWNLOAD_OK;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == DOWNLOAD_OK) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                Uri photoURI = FileProvider.getUriForFile(context,
//                        context.getPackageName() + ".fileProvider", apkFile
//                );
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (Build.VERSION.SDK_INT >= 24) {
                } else {
                    boolean updata = install(apkFile.getPath());
                    Log.e("updata", "updata:"+ updata);
//                    intent.setDataAndType(Uri.parse("file://" + apkFile.getPath()),
//                            "application/vnd.android.package-archive");
                }
//                context.startActivity(intent);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

        }
    }


    /**
     * 执行具体的静默安装逻辑，需要手机ROOT。
     * @param apkPath
     *          要安装的apk文件的路径
     * @return 安装成功返回true，安装失败返回false。
     */
    public boolean install(String apkPath) {
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;
        try {
            // 申请su权限
            Process process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            // 执行pm install命令
            String command = "pm install -r " + apkPath + "\n";
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String msg = "";
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }
            Log.d("TAG", "install msg is " + msg);
            // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功
            if (!msg.contains("Failure")) {
                result = true;
            }
        } catch (Exception e) {
            Log.e("TAG", e.getMessage(), e);
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
            } catch (IOException e) {
                Log.e("TAG", e.getMessage(), e);
            }
        }
        return result;
    }
}
