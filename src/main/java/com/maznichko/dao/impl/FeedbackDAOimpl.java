package com.maznichko.dao.impl;

import com.maznichko.dao.*;
import com.maznichko.dao.entity.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAOimpl extends FeedbackDAO {

    private Feedback createFeedback(ResultSet resultSet) throws DBException {
        Feedback feedback = new Feedback();
        try {
            feedback.setFeedbackID(resultSet.getInt("feedback_id"));
            feedback.setFeedbackText(resultSet.getString("feedback_text"));
            feedback.setRating(resultSet.getInt("rating"));
            feedback.setDate(resultSet.getTimestamp("date"));
            feedback.setRequestID(resultSet.getInt("id_request"));
            feedback.setMasterLogin(resultSet.getString("master_login"));
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
        }
        return feedback;
    }

    @Override
    public List<Feedback> findAll() throws DBException {
        List<Feedback> feedbacks = new ArrayList<>();
        Connection connection = null;
        ResultSet resultSet;
        try {
            connection = dao.connect();
            resultSet = connection
                    .createStatement()
                    .executeQuery(SQLQuery.RequestFeedback.SELECT_ALL_FROM_FEEDBACK);
            while (resultSet.next()) {
                Feedback feedback = createFeedback(resultSet);
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
        } finally {
            dao.close(connection);
        }
        return feedbacks;
    }

    @Override
    public Feedback getData(long id) throws DBException {
        return null;
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
            connection = dao.connect();
            prDelete = connection.prepareStatement(SQLQuery.RequestFeedback.DELETE_FROM_FEEDBACK);
            prDelete.setLong(1, data.getFeedbackID());
            int delResult = prDelete.executeUpdate();
            if (delResult == 0) {
                return false;
            }
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
        } finally {
            dao.close(prDelete);
            dao.close(connection);
        }
        return true;
    }

    @Override
    public boolean insert(Feedback data) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = dao.connect();
            dao.autocommit(connection, false);
            pstmt = connection.prepareStatement(SQLQuery.RequestFeedback.INSERT_FEEDBACK);
            pstmt.setString(1, data.getFeedbackText());
            pstmt.setInt(2, data.getRating());
            pstmt.setLong(3, data.getRequestID());
            pstmt.setString(4, data.getMasterLogin());
            int rs = pstmt.executeUpdate();
            if (rs == 0) {
                dao.rollback(connection);
                return false;
            }
            connection.commit();
        } catch (SQLException e) {
            dao.rollback(connection);
            throw new DBException("SQL Exception", e);
        } finally {
            dao.autocommit(connection, true);
            dao.close(pstmt);
            dao.close(connection);
        }
        return true;
    }
}
