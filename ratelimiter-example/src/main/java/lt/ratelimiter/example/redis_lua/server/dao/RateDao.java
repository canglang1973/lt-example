package lt.ratelimiter.example.redis_lua.server.dao;

import lt.ratelimiter.example.redis_lua.server.domain.RateLimiterInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leitao.
 * @time: 2017/12/11  10:51
 * @version: 1.0
 * @description:
 **/
public class RateDao {
    public List<RateLimiterInfo>  selectAll(){
        Connection conn = RateDBManger.getConnection();
        String sql ="SELECT  * from rate_limiter_info ";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            List<RateLimiterInfo> rateLimiterInfos = new ArrayList<>();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String apps = resultSet.getString("apps");
                int max_permits = resultSet.getInt("max_permits");
                int rate = resultSet.getInt("rate");
                Date create_at = resultSet.getDate("created_at");
                Date updated_at = resultSet.getDate("updated_at");
                RateLimiterInfo info = new RateLimiterInfo();
                info.setApps(apps);
                info.setCreatedAt(create_at);
                info.setId(id);
                info.setMaxPermits(max_permits);
                info.setName(name);
                info.setRate(rate);
                info.setUpdatedAt(updated_at);
                rateLimiterInfos.add(info);
            }
            System.out.println(rateLimiterInfos);
            return rateLimiterInfos;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            RateDBManger.close(conn);
        }
        return null;
    }
    public RateLimiterInfo  selectByName(String name_){
        Connection conn = RateDBManger.getConnection();
        String sql ="SELECT  * from rate_limiter_info where name=?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1,name_);
            ResultSet resultSet = pstm.executeQuery();
            RateLimiterInfo info = new RateLimiterInfo();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String apps = resultSet.getString("apps");
                int max_permits = resultSet.getInt("max_permits");
                int rate = resultSet.getInt("rate");
                Date create_at = resultSet.getDate("created_at");
                Date updated_at = resultSet.getDate("updated_at");
                info.setApps(apps);
                info.setCreatedAt(create_at);
                info.setId(id);
                info.setMaxPermits(max_permits);
                info.setName(name);
                info.setRate(rate);
                info.setUpdatedAt(updated_at);
            }
            System.out.println(info);
            return info;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            RateDBManger.close(conn);
        }
        return null;
    }
    public void  deleteByName(String name){
        Connection conn = RateDBManger.getConnection();
        String sql ="DELETE from rate_limiter_info where name=?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1,name);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            RateDBManger.close(conn);
        }
    }
    public void  saveOrUpdate( String name,String apps,Integer maxPermits,Integer rate){
        Connection conn = RateDBManger.getConnection();
        String sql ="insert into rate_limiter_info(`name`, `apps`, `max_permits`, `rate`) VALUES (?,?,?,?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1,name);
            pstm.setString(2,apps);
            pstm.setInt(3,maxPermits);
            pstm.setInt(4,rate);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            RateDBManger.close(conn);
        }
    }
}
