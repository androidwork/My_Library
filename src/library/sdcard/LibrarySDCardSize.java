package library.sdcard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import android.os.Environment;
import android.os.StatFs;
import android.text.StaticLayout;
import android.util.Base64;
import android.util.Log;

public class LibrarySDCardSize
{
    int ERROR = -1;
    String sdText;
    String sdNull = "没有找到sd卡";
    
    public LibrarySDCardSize()
    {
        
    }
    
    // 判断sd卡是否可用
    private boolean isSDCardAvailable()
    {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }
    
    /**
     * 获取手机外部可用空间大小
     */
    public String getExternalMemorySize()
    {
        /** 如果sd卡可用则进行操作 */
        if (isSDCardAvailable())
        {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            
            double sdSize = (double) availableBlocks * (double) blockSize / 1024 / 1024;
            if (sdSize < 1024)
            {
                sdText = sdSize + "";
                sdText = sdText.substring(0, sdText.indexOf(".") + 2) + "MB";
            } else
            {
                sdSize = sdSize / 1024;
                sdText = sdSize + "";
                sdText = sdText.substring(0, sdText.indexOf(".") + 2) + "GB";
                
            }
            return sdText;
        } else
        {
            return sdNull;
        }
    }
    
    /**
     * 获取手机外部空间大小
     */
    private long getTotalMemorySize()
    {
        /** 如果sd卡可用则进行操作 */
        if (isSDCardAvailable())
        {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else
        {
            return ERROR;
        }
    }
    
    /**
     * 获取SD卡根目录
     * @return null：无SD卡
     *         android.permission.WRITE_EXTERNAL_STORAGE
     *         android.permission.MOUNT_UNMOUNT_FILESYSTEMS
     */
    public String getSDPath()
    {
        File sdDir = null;
        if (isSDCardAvailable())
        {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        return sdDir == null ? null : sdDir.getAbsolutePath();
    }
    
    /**往SD卡中存入数据，追加txt
     * @param path
     * @param text
     * @return
     */
    public boolean appendStringToFile(String path, String text)
    {
        boolean isok = true;
        if (isSDCardAvailable())
        {
            library.tool.StaticTel.DebugError("找不到SD卡");
            return false;
        }
        
        FileWriter fw = null;
        try
        {
            
            File file = new File(path);
            if (!file.exists())
            {
                String p = path.substring(0, path.lastIndexOf("/"));
                File androidFile = new File(p);
                if (!androidFile.exists())
                {
                    androidFile.mkdirs();
                }
                file.createNewFile();
            }
            fw = new FileWriter(new String(file.getAbsolutePath().getBytes(), "gb2312"), true);
        } catch (IOException e)
        {
            isok = false;
            library.tool.StaticTel.DebugError("写入错误");
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(text);
        pw.flush();
        try
        {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e)
        {
            isok = false;
            library.tool.StaticTel.DebugError("关闭流错误");
            e.printStackTrace();
        }
        return isok;
    }
    
    /**
     * 获取文件内容
     * @param filePath
     * @return
     */
    public List<String> getStringToSdFile(String path)
    {
        List<String> list = new ArrayList<String>();
        FileInputStream fInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader in = null;
        try
        {
            File file = new File(path);
            if (!file.exists())
            {
                library.tool.StaticTel.DebugError("读取数据失败 文件不存在");
                return null;
            }
            fInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fInputStream, "utf-8");
            in = new BufferedReader(inputStreamReader);
            while (in.ready())
            {
                String line = in.readLine().trim();
                if (line.length() != 0)
                {
                    list.add(new String(line.getBytes()));
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
                if (inputStreamReader != null)
                {
                    inputStreamReader.close();
                }
                if (fInputStream != null)
                {
                    fInputStream.close();
                }
            } catch (Exception e2)
            {
                // TODO: handle exception
            }
        }
        return list;
    }
}
