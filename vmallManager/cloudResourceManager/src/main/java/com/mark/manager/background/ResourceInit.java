package com.mark.manager.background;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ResourceInit {
    private String filePath = "D:\\wamp\\www\\vmall\\vmallparent\\vmallManager\\cloudResourceManager\\filelist.txt";
    private String recordPath = "D:\\wamp\\www\\vmall\\vmallparent\\vmallManager\\cloudResourceManager\\record.txt";
    private String dirPath = "D:\\study163";
    private String bucketName = "vpro-1258194404";
    private static COSClient client = null;

    /**
     * 获得文件列表list
     * @return
     */
    public List<String> getResourceList() {
        File listFile = new File(filePath);
        List<String> list = new ArrayList<String>();
        if (!listFile.exists()) {
            File dir = new File(dirPath);
            File[] fs = dir.listFiles();
            for (File f : fs) {
                if (!f.isDirectory()) {
                    list.add(f.getPath());
                }
            }
            genFileList(list);
        } else {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(listFile));
                String str;
                while((str = bufferedReader.readLine())!=null) {
                    list.add(str);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 创建文件列表
     * @param list
     */
    public void genFileList(List<String> list) {
        try {
            File file = new File(filePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for(String s : list) {
                s += "\n";
                byte[] sb = s.getBytes();
                fileOutputStream.write(sb);
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传
     * @param file
     * @return
     */
    public String put2Qcloud(File file) {
        COSClient cosClient = getCosClient();
        // 方法2 从输入流上传(需提前告知输入流的长度, 否则可能导致 oom)
        FileInputStream fileInputStream = null;
        String etag = null;
        try {
            fileInputStream = new FileInputStream(file);
            ObjectMetadata objectMetadata = new ObjectMetadata();
// 设置输入流长度为500
            objectMetadata.setContentLength(file.length());
// 设置 Content type, 默认是 application/octet-stream
//        objectMetadata.setContentType("application/pdf");

            PutObjectResult putObjectResult = cosClient.putObject(bucketName, file.getName(), fileInputStream, objectMetadata);
            etag = putObjectResult.getETag();
// 关闭输入流...
            fileInputStream.close();
            System.out.println(etag);
            if (etag != null) return file.getName();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 获得客户端对象
     * @return
     */
    public COSClient getCosClient() {
        if (client == null) {
            // 1 初始化用户身份信息(secretId, secretKey)
            COSCredentials cred = new BasicCOSCredentials("AKIDoTwLlEQq8UedWFHDRitwobEj3BxVpDLT", "60lT6e03JPJSt9FFi2AoLgtxuq1Vs3DC");
            // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            // clientConfig中包含了设置region, https(默认http), 超时, 代理等set方法, 使用可参见源码或者接口文档FAQ中说明
            ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
            // 3 生成cos客户端
            client = new COSClient(cred, clientConfig);
        }
        return client;
    }

    /**
     * 迭代文件列表
     */
    @PostConstruct
    public void iterFileList() {
        List<String> list = getResourceList();
        for(String s : list) {
            File file = new File(s);
            String name = put2Qcloud(file);
            if (name != null) {
                record(name);
            }
        }
    }

    /**
     * 记录成功文件
     * @param name
     */
    public void record(String name) {
        File file = new File(recordPath);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            byte[] nb = (name + "\n").getBytes();
            fileOutputStream.write(nb);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
