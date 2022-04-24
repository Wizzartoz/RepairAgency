package com.maznichko.DAO;

import com.maznichko.DAO.entity.Feedback;
import com.maznichko.DAO.entity.Request;
import com.maznichko.DAO.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {
    public static void main(String[] args) {
        Feedback feedback;
        try {
            feedback = new FeedbackDAO().getFeedback(7);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        System.out.println(feedback.getFeedbackID());
    }

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
            throw new DBException("Create feedback was failed!", e);
        }
        return feedback;
    }

    public boolean insertFeedback(Feedback feedback) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = DBManager.getInstance().connect();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(SQLQuery.RequestFeedback.INSERT_FEEDBACK);
            pstmt.setString(1, feedback.getFeedbackText());
            pstmt.setInt(2, feedback.getRating());
            pstmt.setInt(3, feedback.getRequestID());
            pstmt.setString(4, feedback.getMasterLogin());
            int rs = pstmt.executeUpdate();
            if (rs == 0) {
                DBManager.getInstance().rollback(connection);
                return false;
            }
            connection.commit();
        } catch (SQLException e) {
            DBManager.getInstance().rollback(connection);
            throw new DBException("Insert feedback didn't failed", e);
        } finally {
            DBManager.getInstance().close(pstmt)
                    .close(connection);

        }
        return true;
    }

    public boolean deleteFeedback(Feedback feedback) throws DBException {
        Connection connection = null;
        PreparedStatement prDelete = null;
        try {
            connection = DBManager.getInstance().connect();
            prDelete = connection.prepareStatement(SQLQuery.RequestFeedback.DELETE_FROM_FEEDBACK);
            prDelete.setInt(1, feedback.getFeedbackID());
            int delResult = prDelete.executeUpdate();
            if (delResult == 0) {
                return false;
            }
        } catch (SQLException e) {
            throw new DBException("Delete feedback was failed", e);
        } finally {
            DBManager.getInstance().close(prDelete)
                    .close(connection);
        }
        return true;
    }

    public Feedback getFeedback(Integer requestID) throws DBException {
        Connection connection = null;
        PreparedStatement prGet = null;
        Feedback feedback = null;
        try {
            connection = DBManager.getInstance().connect();
            prGet = connection.prepareStatement(SQLQuery.RequestFeedback.SELECT_ALL_FROM_FEEDBACK_WHERE);
            prGet.setInt(1, requestID);
            ResultSet rs = prGet.executeQuery();
            if (rs.next()) {
                feedback = createFeedback(rs);
            }
        } catch (SQLException e) {
            throw new DBException("Get feedback was failed", e);
        } finally {
            DBManager.getInstance().close(prGet)
                    .close(connection);
        }
        return feedback;
    }


    public List<Feedback> findAllFeedback() throws DBException {
        List<Feedback> feedbacks = new ArrayList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = DBManager.getInstance().connect();
            resultSet = connection
                    .createStatement()
                    .executeQuery(SQLQuery.RequestFeedback.SELECT_ALL_FROM_FEEDBACK);
            while (resultSet.next()) {
                Feedback feedback = createFeedback(resultSet);
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            throw new DBException("Find all feedback was failed", e);
        } finally {
            DBManager.getInstance().close(resultSet)
                    .close(connection);
        }
        return feedbacks;
    }
}
