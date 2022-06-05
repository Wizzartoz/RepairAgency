package com.maznichko.dao.impl;

import com.maznichko.dao.*;
import com.maznichko.dao.entity.Feedback;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAOimpl extends FeedbackDAO {
    private static final Logger log = Logger.getLogger(FeedbackDAOimpl.class);

    private Feedback createFeedback(ResultSet resultSet) throws DBException {
        Feedback feedback = new Feedback();
        try {
            feedback.setFeedbackID(resultSet.getInt("feedback_id"));
            feedback.setFeedbackText(resultSet.getString("feedback_text"));
            feedback.setRating(resultSet.getInt("rating"));
            feedback.setDate(resultSet.getTimestamp("date"));
            feedback.setRequestID(resultSet.getInt("id_request"));
            feedback.setMasterLogin(resultSet.getString("master_login"));
            log.info("feedback was created successfully ID:" + feedback.getFeedbackID());
        } catch (SQLException e) {
            log.error(e.getMessage() + " cannot create feedback");
            throw new DBException("SQL Exception: cannot create feedback", e);
        }
        return feedback;
    }

    @Override
    public List<Feedback> findAll() throws DBException {
        List<Feedback> feedbacks = new ArrayList<>();
        Connection connection = null;
        ResultSet resultSet;
        try {
            connection = DBCPDataSource.getConnection();
            resultSet = connection
                    .createStatement()
                    .executeQuery(SQLQuery.RequestFeedback.SELECT_ALL_FROM_FEEDBACK);
            while (resultSet.next()) {
                Feedback feedback = createFeedback(resultSet);
                feedbacks.add(feedback);
            }
            log.info("All feedbacks was founded successfully");
        } catch (SQLException e) {
            log.error(e.getMessage() + " cannot find all feedback");
            throw new DBException("SQL Exception: cannot find all feedback", e);
        } finally {
            DBCPDataSource.close(connection);
        }
        return feedbacks;
    }

    @Override
    public Feedback getData(long id) throws DBException {
        Feedback feedback;
        Connection connection = null;
        PreparedStatement prGet = null;
        try {
            connection = DBCPDataSource.getConnection();
            prGet = connection.prepareStatement(SQLQuery.RequestFeedback.SELECT_ALL_FROM_FEEDBACK_WHERE);
            prGet.setLong(1, id);
            ResultSet rs = prGet.executeQuery();
            rs.next();
            feedback = createFeedback(rs);
            log.info("get feedback was successfully ID:" + feedback.getFeedbackID());
        } catch (SQLException e) {
            log.error(e.getMessage() + " cannot get feedback");
            throw new DBException("SQL Exception: cannot get feedback", e);
        } finally {
            DBCPDataSource.close(prGet);
            DBCPDataSource.close(connection);
        }
        return feedback;
    }

    @Override
    public boolean update(Feedback data) throws DBException {
        return false;
    }

    @Override
    public boolean delete(Feedback data) throws DBException {
        Connection connection = null;
        PreparedStatement prDelete = null;
        try {
            connection = DBCPDataSource.getConnection();
            prDelete = connection.prepareStatement(SQLQuery.RequestFeedback.DELETE_FROM_FEEDBACK);
            prDelete.setLong(1, data.getFeedbackID());
            int delResult = prDelete.executeUpdate();
            if (delResult == 0) {
                return false;
            }
            log.info("delete feedback was successfully ID:" + data.getFeedbackID());
        } catch (SQLException e) {
            log.error(e.getMessage() + " cannot delete feedback");
            throw new DBException("SQL Exception: cannot delete feedback", e);
        } finally {
            DBCPDataSource.close(prDelete);
            DBCPDataSource.close(connection);
        }
        return true;
    }

    @Override
    public boolean insert(Feedback data) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = DBCPDataSource.getConnection();
            DBCPDataSource.autocommit(connection, false);
            pstmt = connection.prepareStatement(SQLQuery.RequestFeedback.INSERT_FEEDBACK);
            pstmt.setString(1, data.getFeedbackText());
            pstmt.setInt(2, data.getRating());
            pstmt.setLong(3, data.getRequestID());
            pstmt.setString(4, data.getMasterLogin());
            int rs = pstmt.executeUpdate();
            if (rs == 0) {
                DBCPDataSource.rollback(connection);
                return false;
            }
            connection.commit();
            log.info("insert feedback was successfully");
        } catch (SQLException e) {
            log.error(e.getMessage() + " cannot insert feedback");
            DBCPDataSource.rollback(connection);
            throw new DBException("SQL Exception: cannot insert feedback", e);
        } finally {
            DBCPDataSource.autocommit(connection, true);
            DBCPDataSource.close(pstmt);
            DBCPDataSource.close(connection);
        }
        return true;
    }
}
