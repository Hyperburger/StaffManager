package hyperburger.me.staffmanager.report;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ReportManager {

  //  private final MongoCollection<Document> reportsCollection;

    @Getter
    private final HashMap<UUID, Report> reports;
    private final MongoCollection<Document> reportsCollection;

    public ReportManager(MongoDatabase database){
        reports = new HashMap<>();
        this.reportsCollection = database.getCollection("reports");

    // this.reportsCollection = database.getCollection("ReportsList");


    }

    public void addReport(Player reporter, Player reported, String reason, long time){
        Report report = new Report(reporter, reported, reason, time);
        reports.put(report.getId(), report);

        Document reportDocument = new Document()
                .append("reporter", report.getReporter().getName())
                .append("reported", report.getReported().getName())
                .append("reason", report.getReason())
                .append("timestamp", report.getTimestamp());
        reportsCollection.insertOne(reportDocument);
       // reportsCollection.insertOne(reportDocument);

    }

}
