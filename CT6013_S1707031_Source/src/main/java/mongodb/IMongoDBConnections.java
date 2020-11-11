package mongodb;

import org.bson.Document;

public interface IMongoDBConnections {

    void registerCustomerToDB(Document customer);

}
