package net.kernevez.pbhibernateproxy.sql;

import io.hypersistence.tsid.TSID;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.EnhancedUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class TsidType implements EnhancedUserType<TSID> {
    @Override
    public int getSqlType() {
        return Types.BIGINT;
    }

    @Override
    public Class<TSID> returnedClass() {
        return TSID.class;
    }

    @Override
    public boolean equals(TSID x, TSID y) {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(TSID x) {
        return x.hashCode();
    }

    @Override
    public TSID nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String tsidStr = rs.getString(position);
        if (tsidStr == null) {
            return null;
        }
        return TSID.from(Long.parseLong(tsidStr));
    }

    @Override
    public void nullSafeSet(PreparedStatement st, TSID value, int index,
                            SharedSessionContractImplementor session) throws SQLException {

        if (Objects.isNull(value))
            st.setNull(index, getSqlType());
        else {
            st.setLong(index, value.toLong());
        }
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public TSID deepCopy(TSID value) {
        return value; //TSID is immutable
    }

    @Override
    public Serializable disassemble(TSID tsid) {
        return tsid; //TSID is immutable
    }

    @Override
    public TSID assemble(Serializable cached, Object owner) {
        return (TSID) cached; //TSID is immutable
    }

    @Override
    public String toSqlLiteral(TSID tsid) {
        return "%d".formatted(tsid.toLong());
    }

    @Override
    public String toString(TSID tsid) throws HibernateException {
        return tsid.toString();
    }

    @Override
    public TSID fromStringValue(CharSequence charSequence) throws HibernateException {
        return TSID.from(Long.parseLong(charSequence.toString()));
    }
}
