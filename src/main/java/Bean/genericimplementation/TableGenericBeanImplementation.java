/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean.genericimplementation;

import Bean.publicinterface.GenericBeanInterface;
import Bean.specificimplementation.UsuarioSpecificBeanImplementation;
import Dao.publicinterface.TableDaoInterface;
import Helper.EncodingUtilHelper;
import Helper.Log4jConfigurationHelper;
import Helper.MappingDaoHelper;
import com.google.gson.annotations.Expose;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Kysuke
 */
public abstract class TableGenericBeanImplementation extends ViewGenericBeanImplementation implements GenericBeanInterface {

    @Expose
    protected Integer id;

    public TableGenericBeanImplementation() {

    }

    public TableGenericBeanImplementation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getColumns() throws Exception {
        String strColumns = "";
        try {
            TableGenericBeanImplementation oBean = (TableGenericBeanImplementation) Class.forName(this.getClass().getName()).newInstance();
            Field[] oFields = oBean.getClass().getDeclaredFields();
            for (Field x : oFields) {
                if (!x.getName().startsWith("obj_")) {
                    strColumns += x.getName() + ",";
                }
            }
            strColumns = strColumns.substring(0, strColumns.length() - 1);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName();
            Log4jConfigurationHelper.errorLog(msg, ex);
            throw new Exception(msg, ex);
        }
        return strColumns;
    }

    @Override
    public String getValues() throws Exception {
        String strColumns = "";
        try {
            TableGenericBeanImplementation oBean = (TableGenericBeanImplementation) Class.forName(this.getClass().getName()).newInstance();
            Field[] oFields = oBean.getClass().getDeclaredFields();
            for (Field x : oFields) {
                x.setAccessible(true);
                if (!x.getName().startsWith("obj_")) {
                    if (x.getName().equals("pass")) {
                        strColumns += EncodingUtilHelper.quotate("da8ab09ab4889c6208116a675cad0b13e335943bd7fc418782d054b32fdfba04") + ",";
                    } else {
                        if (x.getType() == String.class) {
                            strColumns += EncodingUtilHelper.quotate((String) x.get(this)) + ",";
                        }
                        if (x.getType() == Date.class) {
                            strColumns += EncodingUtilHelper.stringifyAndQuotate((Date) x.get(this)) + ",";
                        }
                        if (x.getType() == Integer.class) {
                            strColumns += x.get(this) + ",";
                        }
                        if (x.getType() == Double.class) {
                            strColumns += x.get(this) + ",";
                        }
                    }

                }
                x.setAccessible(false);
            }
            if (!strColumns.equals("")) {
                strColumns = strColumns.substring(0, strColumns.length() - 1);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName();
            Log4jConfigurationHelper.errorLog(msg, ex);
            throw new Exception(msg, ex);
        }
        return strColumns;
    }

    @Override
    public String toPairs() throws Exception {
        String strPairs = "";
        try {
            TableGenericBeanImplementation oBean = (TableGenericBeanImplementation) Class.forName(this.getClass().getName()).newInstance();
            Field[] oFields = oBean.getClass().getDeclaredFields();
            for (Field x : oFields) {
                x.setAccessible(true);
                if (!x.getName().startsWith("obj_")) {
                    if (x.getName().equals("pass")) {
                        strPairs += x.getName() + "=" + EncodingUtilHelper.quotate("da8ab09ab4889c6208116a675cad0b13e335943bd7fc418782d054b32fdfba04") + ",";
                    } else {
                        if (x.getType() == String.class) {
                            strPairs += x.getName() + "=" + EncodingUtilHelper.quotate((String) x.get(this)) + ",";
                        }
                        if (x.getType() == Date.class) {
                            strPairs += x.getName() + "=" + EncodingUtilHelper.stringifyAndQuotate((Date) x.get(this)) + ",";
                        }
                        if (x.getType() == Integer.class) {
                            strPairs += x.getName() + "=" + x.get(this) + ",";
                        }
                        if (x.getType() == Double.class) {
                            strPairs += x.getName() + "=" + x.get(this) + ",";
                        }
                    }

                }
                x.setAccessible(false);
            }
            if (!strPairs.equals("")) {
                strPairs = strPairs.substring(0, strPairs.length() - 1);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName();
            Log4jConfigurationHelper.errorLog(msg, ex);
            throw new Exception(msg, ex);
        }
        return strPairs;

    }

    @Override
    public GenericBeanInterface fill(ResultSet oResultSet, Connection oConnection, UsuarioSpecificBeanImplementation oPuserBean_security, Integer expand) throws Exception {
        ViewGenericBeanImplementation oBean = (ViewGenericBeanImplementation) Class.forName(this.getClass().getName()).newInstance();
        if (this.getClass().getSuperclass() == TableGenericBeanImplementation.class) {
            Field x = this.getClass().getSuperclass().getDeclaredField("id");
            x.setAccessible(true);
            x.set(this, oResultSet.getInt("id"));
            x.setAccessible(false);
        }
        Field[] oFields = oBean.getClass().getDeclaredFields();
        for (Field x : oFields) {
            x.setAccessible(true);
            if (x.getName().startsWith("obj_")) {
                if (expand > 0) {
                    String ob = x.getName().substring(x.getName().indexOf("_") + 1);
                    TableDaoInterface oObDao = (TableDaoInterface) MappingDaoHelper.getDao(ob, oConnection, oPuserBean_security, null);
                    TableGenericBeanImplementation oObBean = (TableGenericBeanImplementation) oObDao.get(oResultSet.getInt("id_" + ob), expand - 1);
                    x.set(this, oObBean);
                }
            } else {
                if (x.getName().startsWith("id_")) {
                    x.set(this, oResultSet.getInt(x.getName()));
                } else {
                    if (x.getType() == String.class) {
                        x.set(this, oResultSet.getString(x.getName()));
                    }
                    if (x.getType() == Date.class) {
                        x.set(this, oResultSet.getDate(x.getName()));
                    }
                    if (x.getType() == Double.class) {
                        x.set(this, oResultSet.getDouble(x.getName()));
                    }
                    if (x.getType() == Integer.class || x.getType() == int.class) {
                        x.set(this, oResultSet.getInt(x.getName()));
                    }
                }
            }
            x.setAccessible(false);
        }
        return this;
    }

}
