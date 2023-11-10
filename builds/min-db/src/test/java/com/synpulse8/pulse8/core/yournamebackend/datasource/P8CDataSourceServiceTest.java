package com.synpulse8.pulse8.core.yournamebackend.datasource;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@Log4j2
@ExtendWith(MockitoExtension.class)
class P8CDataSourceServiceTest {

    @InjectMocks
    private P8CDataSourceService dataSourceService;

    @Mock
    private DataSource dataSource;

    @Mock
    private P8CTenantIdentifierResolver tenantIdentifierResolver;

    @Mock
    private Connection connection;

    @Test
    void getDataSource() {
        DataSource dataSource = this.dataSourceService.getDataSource();
        assertThat(this.dataSource).isEqualTo(dataSource);
    }

    @Test
    void getAnyConnection() {
        try {
            assertThrows(NullPointerException.class, () -> {
                this.dataSourceService.getAnyConnection();
            });
            verify(this.dataSource).getConnection();
            verify(tenantIdentifierResolver).getDefaultTenant();
        } catch (SQLException ex) {
            log.debug(ex);
        }
    }

    @Test
    void releaseAnyConnection() {
        try {
            this.dataSourceService.releaseAnyConnection(connection);
            verify(connection).close();
        } catch (SQLException ex) {
            log.debug(ex);
        }
    }

    @Test
    void getConnection() {
        assertThrows(NullPointerException.class, () -> {
            Connection result = this.dataSourceService.getConnection("test");
        });
    }

    @Test
    void releaseConnection() {
        try {
            this.dataSourceService.releaseConnection("test", connection);
            verify(connection).close();
        } catch (SQLException ex) {
            log.debug(ex);
        }
    }

    @Test
    void supportsAggressiveRelease() {
        assertFalse(this.dataSourceService.supportsAggressiveRelease());
    }

    @Test
    void customize() {
        Map<String, Object> map = new HashMap<>();
        this.dataSourceService.customize(map);
        assertThat(map).isNotEmpty();
    }

    @Test
    void isUnwrappableAs() {
        assertFalse(this.dataSourceService.isUnwrappableAs(Object.class));
    }

    @Test
    void unwrap() {
        assertNull(this.dataSourceService.unwrap(Object.class));
    }

}
