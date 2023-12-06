package Main;

import beans.new_msg;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class collectMes {

    public static int[] oldMesCount = new int[16];
    public static int[] newMesCount = new int[16];
    public static String Driver = "com.mysql.cj.jdbc.Driver";
    public static String[] url_list = {
            "jdbc:mysql://172.23.94.244:3306/databus?characterEncoding=utf-8",
            "jdbc:mysql://172.23.94.51:3306/databus?characterEncoding=utf-8",
            "jdbc:mysql://172.23.94.52:3306/databus?characterEncoding=utf-8",
     //       "jdbc:mysql://172.23.94.212:3306/databus?characterEncoding=utf-8",
            "jdbc:mysql://172.23.94.19:3306/databus?characterEncoding=utf-8",
            "jdbc:mysql://172.23.94.20:3306/databus?characterEncoding=utf-8",
            "jdbc:mysql://172.23.94.148:3306/databus?characterEncoding=utf-8",
            "jdbc:mysql://172.23.94.116:3306/databus?characterEncoding=utf-8",
            "jdbc:mysql://172.23.95.19:3306/databus?characterEncoding=utf-8",
            "jdbc:mysql://172.23.95.20:3306/databus?characterEncoding=utf-8",
            "jdbc:mysql://172.23.94.84:3306/databus?characterEncoding=utf-8",
            "jdbc:mysql://172.23.94.179:3306/databus?characterEncoding=utf-8&useSSL=false",
            "jdbc:mysql://172.23.94.180:3306/databus?characterEncoding=utf-8",
            "jdbc:mysql://172.23.95.52:3306/databus?characterEncoding=utf-8",
            "jdbc:mysql://172.23.95.84:3306/databus?characterEncoding=utf-8",
            "jdbc:mysql://172.23.95.116:3306/databus?characterEncoding=utf-8",
    };
    public static String[] Name = {
            "tbu-hf-mdc01", "tbu-xm-mdc01", "tbu-xm-mdc02",  "tbu-qd-mdc01", "tbu-qd-mdc02", "tbu-nn-mdc01", "tbu-ya-mdc01",
            "tbu-jy-mdc01", "tbu-jy-mdc02", "tbu-sn-mdc01", "tbu-xg-mdc01", "tbu-xg-mdc02", "tbu-tj-mdc01", "tbu-xy-mdc01", "tbu-cs-mdc01",
    };
    public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /*
     * 返回报警记录列表
     */
    public static List<new_msg> mes_List() {

        List<new_msg> mesList = new ArrayList<>();  //报警记录列表
        new_msg msg;
        //查询最新报警ID
        String event_count = "SELECT\n" +
                "event_attribute.id\n" +
                "FROM\n" +
                "event_attribute\n" +
                "ORDER BY\n" +
                "event_attribute.id DESC\n" +
                "LIMIT 1\n";

        for (int index = 0; index < url_list.length; index++) {

            int newMes;  //新记录条数

            try {
                Class.forName(Driver);
                Connection connection = DriverManager.getConnection(url_list[index], "root", "root");
                Statement state = connection.createStatement();

                ResultSet resultSet = state.executeQuery(event_count);
                while (resultSet.next()) {
                    newMesCount[index] = resultSet.getInt(1);
                }

//                System.out.println("当前查询："+Name[index]+"  index="+index);
//                System.out.println("Name:"+ Name[index] +"\t newMesCount:" + newMesCount[index] + "\t oldMesCount:" + oldMesCount[index]);

                //第一次运行时赋值
                if (oldMesCount[index] == 0) {
                    oldMesCount[index] = newMesCount[index];
                }
                if (newMesCount[index] > oldMesCount[index]) {
                    newMes = newMesCount[index] - oldMesCount[index];
                } else newMes = 0;
                String update_query = "SELECT\n" +
                        "resource.`name`,\n" +
                        "event_attribute.content,\n" +
                        "`event`.create_time,\n" +
                        "`event`.fix_time,\n" +
                        "`event`.id\n" +
                        "FROM\n" +
                        "event_attribute ,\n" +
                        "`event` ,\n" +
                        "resource\n" +
                        "WHERE\n" +
                        "event_attribute.id = `event`.event_attribute_id AND\n" +
                        "`event`.resource_code = resource.`code`\n" +
                        "ORDER BY\n" +
                        "event_attribute.id DESC\n" +
                        "LIMIT " + newMes;
                if (newMes != 0) {
                    resultSet = state.executeQuery(update_query);
                }
                while (resultSet.next()) {
                    String dev_name = resultSet.getString(1);
                    String des = resultSet.getString(2);
                    String create_date = resultSet.getString(3);
                    String fixed_date = resultSet.getString(4);
                    int event_id = resultSet.getInt(5);
                    msg = new new_msg(Name[index], dev_name, des, create_date, fixed_date, event_id);
                    mesList.add(msg);
                }
                resultSet.close();
                state.close();
                connection.close();
            } catch (Exception e) {

                System.out.println();

            } finally {
                oldMesCount[index] = newMesCount[index];
            }
        }
        return mesList;
    }

    public static List<new_msg> update_mes(List<new_msg> last_list) {

        List<new_msg> update_list = new ArrayList<>();
        new_msg update_mes;

        for(new_msg x : last_list){
            for(int i = 0; i < 16; i++) {
                if (x.getName().equals(Name[i])) {
                    String sql = "SELECT\n" +
                            "resource.`name`,\n" +
                            "event_attribute.content,\n" +
                            "`event`.create_time,\n" +
                            "`event`.fix_time,\n" +
                            "`event`.id\n" +
                            "FROM\n" +
                            "event_attribute ,\n" +
                            "`event` ,\n" +
                            "resource\n" +
                            "WHERE\n" +
                            "event_attribute.id = `event`.event_attribute_id AND\n" +
                            "`event`.resource_code = resource.`code` AND\n" +
                            "`event`.id = " + x.getEvent_id();
                    try {
                        Class.forName(Driver);
                        Connection connection = DriverManager.getConnection(url_list[i], "root", "root");
                        Statement state = connection.createStatement();
                        ResultSet resultSet = state.executeQuery(sql);
                        while (resultSet.next()) {
                            String dev_name = resultSet.getString(1);
                            String des = resultSet.getString(2);
                            String create_date = resultSet.getString(3);
                            String fixed_date = resultSet.getString(4);
                            int event_id = resultSet.getInt(5);
                            update_mes = new new_msg(Name[i], dev_name, des, create_date, fixed_date, event_id);
                            update_list.add(update_mes);
                        }
                        resultSet.close();
                        state.close();
                        connection.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return update_list;
    }
}
