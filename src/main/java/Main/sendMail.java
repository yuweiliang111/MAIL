package Main;

import beans.new_msg;

import java.io.*;
import java.lang.Runtime;
import java.lang.Process;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static Main.collectMes.*;

public class sendMail {

    public static String[] cmd = new String[]{"/bin/sh", "-c", "mailx -s 'MDC alarm' -r mdc-server@stulz.com lichenguang@app.com.cn < output.txt"};
    public static String[] qd = new String[]{"/bin/sh", "-c", "mailx -s 'QD MDC alarm' -r mdc-server@stulz.com taipei@ghy.com.cn < qingdao.txt"};
    public static String[] xm = new String[]{"/bin/sh", "-c", "mailx -s 'XM MDC alarm' -r mdc-server@stulz.com chenghai@ghy.com.cn < xinmin.txt"};
    public static String[] sn = new String[]{"/bin/sh", "-c", "mailx -s 'SN MDC alarm' -r mdc-server@stulz.com wangwei_ghy6@ghy.com.cn < suining.txt"};
    public static String[] ya = new String[]{"/bin/sh", "-c", "mailx -s 'YA MDC alarm' -r mdc-server@stulz.com yangchanglong@ghy.com.cn < yaan.txt"};
    public static String[] nn = new String[]{"/bin/sh", "-c", "mailx -s 'NN MDC alarm' -r mdc-server@stulz.com zhangxianming@ghy.com.cn < nanning.txt"};
    public static String[] xg = new String[]{"/bin/sh", "-c", "mailx -s 'XG MDC alarm' -r mdc-server@stulz.com wanghaibo_ghy@ghy.com.cn < xiaogan.txt"};
   // public static String[] fz = new String[]{"/bin/sh", "-c", "mailx -s 'FZ MDC alarm' -r mdc-server@stulz.com lixinjie@ghy.com.cn < fuzhou.txt"};
    public static String[] hf = new String[]{"/bin/sh", "-c", "mailx -s 'HF MDC alarm' -r mdc-server@stulz.com shiyang_it@ghy.com.cn < hefei.txt"};
    public static String[] jy = new String[]{"/bin/sh", "-c", "mailx -s 'JY MDC alarm' -r mdc-server@stulz.com huangguining@app.com.cn < jinyu.txt"};
    public static String[] tj = new String[]{"/bin/sh", "-c", "mailx -s 'TJ MDC alarm' -r mdc-server@stulz.com wangxu_tj@ghy.com.cn < tianjin.txt"};
    public static String[] xy = new String[]{"/bin/sh", "-c", "mailx -s 'XY MDC alarm' -r mdc-server@stulz.com zhaonan@ghy.com.cn < xianyang.txt"};
    public static String[] cs = new String[]{"/bin/sh", "-c", "mailx -s 'CS MDC alarm' -r mdc-server@stulz.com chengang_cs@ghy.com.cn < changsha.txt"};

    public static List<new_msg> last_list = new ArrayList<>();  //暂存上次结果
    /**
     * 计算分钟差
     * @param from 警报创建时间
    */
    public static int compareTime(String from) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date fromDate = df.parse(from);
        String now = df.format(System.currentTimeMillis());
        Date toDate = df.parse(now);

        long from1 = fromDate.getTime();
        long to1 = toDate.getTime();
        return  (int) (to1-from1)/(1000*60);
    }
    /**
     * @param list 创建一封只包含文本的邮件
     */
    public static void createMessageFile(OutputStream[] os, PrintWriter[] pw, List<new_msg> list, int[] alarm) throws IOException, ParseException {

        os[0] = new FileOutputStream("output.txt");
        os[1] = new FileOutputStream("qingdao.txt");
        os[2] = new FileOutputStream("xinmin.txt");
        os[3] = new FileOutputStream("suining.txt");
        os[4] = new FileOutputStream("yaan.txt");
        os[5] = new FileOutputStream("nanning.txt");
        os[6] = new FileOutputStream("xiaogan.txt");
        os[7] = new FileOutputStream("fuzhou.txt");
        os[8] = new FileOutputStream("hefei.txt");
        os[9] = new FileOutputStream("jinyu.txt");
        os[10] = new FileOutputStream("tianjin.txt");
        os[11] = new FileOutputStream("xianyang.txt");
        os[12] = new FileOutputStream("changsha.txt");
        for (int i = 0; i < 13; i++) {
            pw[i] = new PrintWriter(os[i]);
            //清空原文件内容
            pw[i].print("");
            pw[i].flush();
        }
        int del_mes = 0;
        //报警信息写入文件
        for (new_msg msg : list) {
            if (msg.getFixed_date() == null) {
                if (compareTime(msg.getCreate_date()) > 3) {
                    String message = "Name：" + msg.getName() + "\tDevice：" + msg.getDevice_name() + "\t" +
                            "Description：" + msg.getDescription() + "\tCreateTime：" + msg.getCreate_date();
                    pw[0].println(message);
                    alarm[0]++;
                    if (msg.getName().equals("tbu-hf-mdc01")) {
                        pw[8].println(message);
                        alarm[8]++;
                    }
                    if (msg.getName().equals("tbu-xm-mdc01") || msg.getName().equals("tbu-xm-mdc02")) {
                        pw[2].println(message);
                        alarm[2]++;
                    }
//                    if (msg.getName().equals("tbu-fz-mdc01")) {
//                        pw[7].println(message);
//                        alarm[7]++;
//                    }
                    if (msg.getName().equals("tbu-qd-mdc01") || msg.getName().equals("tbu-qd-mdc02")) {
                        pw[1].println(message);
                        alarm[1]++;
                    }
                    if (msg.getName().equals("tbu-nn-mdc01")) {
                        pw[5].println(message);
                        alarm[5]++;
                    }
                    if (msg.getName().equals("tbu-ya-mdc01")) {
                        pw[4].println(message);
                        alarm[4]++;
                    }
                    if (msg.getName().equals("tbu-jy-mdc01") || msg.getName().equals("tbu-jy-mdc02")) {
                        pw[9].println(message);
                        alarm[9]++;
                    }
                    if (msg.getName().equals("tbu-sn-mdc01")) {
                        pw[3].println(message);
                        alarm[3]++;
                    }
                    if (msg.getName().equals("tbu-xg-mdc01") || msg.getName().equals("tbu-xg-mdc02")) {
                        pw[6].println(message);
                        alarm[6]++;
                    }
                    if (msg.getName().equals("tbu-tj-mdc01")) {
                        pw[10].println(message);
                        alarm[10]++;
                    }
                    if (msg.getName().equals("tbu-xy-mdc01")) {
                        pw[11].println(message);
                        alarm[11]++;
                    }
                    if (msg.getName().equals("tbu-cs-mdc01")) {
                        pw[12].println(message);
                        alarm[12]++;
                    }
                } else last_list.add(msg);
            }else del_mes++;
        }
        //关闭流
        for (int i = 0; i < os.length; i++) {
            pw[i].close();
            os[i].close();
        }
        System.out.println("移除警报 " + del_mes + " 条, 报警延迟 " + last_list.size() + " 条");
    }
    /**
     * @param cmd 需要执行的命令
     */
    public static void shell(String[] cmd, StringBuffer sb) throws IOException {

        Process ps = Runtime.getRuntime().exec(cmd);
        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        ps.destroy();
    }

    public static void main() throws Exception {

        OutputStream[] os = new OutputStream[13];
        PrintWriter[] pw = new PrintWriter[13];
        StringBuffer sb = new StringBuffer();
        int[] alarm = new int[13];
        int count = 1;

        while (true) {

            System.out.println("第 " + count++ + " 次查询");
            List<new_msg> new_list = mes_List();
            System.out.println("新增报警: " + new_list.size() + " 条");
            if(!last_list.isEmpty()) {
                List<new_msg> update_list = update_mes(last_list);
                update_list.removeIf(msg -> msg.getFixed_date() != null);
                System.out.println("延迟报警写入 " +update_list.size() + " 条");
                new_list.addAll(update_list);
            }
            Arrays.fill(alarm, 0);
            last_list.clear();

            if (new_list.size() != 0) {
                createMessageFile(os, pw, new_list, alarm);
                if (alarm[0] != 0) shell(cmd, sb);
                if (alarm[1] != 0) shell(qd, sb);
                if (alarm[2] != 0) shell(xm, sb);
                if (alarm[3] != 0) shell(sn, sb);
                if (alarm[4] != 0) shell(ya, sb);
                if (alarm[5] != 0) shell(nn, sb);
                if (alarm[6] != 0) shell(xg, sb);
            //    if (alarm[7] != 0) shell(fz, sb);
                if (alarm[8] != 0) shell(hf, sb);
                if (alarm[9] != 0) shell(jy, sb);
                if (alarm[10] != 0) shell(tj, sb);
                if (alarm[11] != 0) shell(xy, sb);
                if (alarm[12] != 0) shell(cs, sb);
            }
            try {
                System.out.println("Date: " + df.format(System.currentTimeMillis()));
                Thread.sleep(600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}