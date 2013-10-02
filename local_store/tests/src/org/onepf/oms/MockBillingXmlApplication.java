package org.onepf.oms;

import android.test.mock.MockApplication;
import org.onepf.oms.data.Database;

public class MockBillingXmlApplication extends MockApplication implements IBillingApplication {

    Database _database;
    String _xml;

    public MockBillingXmlApplication(String xml) {
        _xml = xml;
    }

    @Override
    public Database getDatabase() {
        return _database;
    }

    @Override
    public void onCreate() {
        try {
            _database = new Database();
            _database.deserializeFromOnePFXML(_xml);
        } catch (Exception e) {
            _database = new Database();
        }
    }
}
