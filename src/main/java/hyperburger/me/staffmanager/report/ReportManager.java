package hyperburger.me.staffmanager.report;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.*;

public class ReportManager {

    @Getter
    private final HashMap<UUID, Report> reports;

    private final MongoCollection<Document> reportsCollection;

    public ReportManager(MongoDatabase database){
        reports = new HashMap<>();

        this.reportsCollection = database.getCollection("reports");
    }

    public UUID addReport(Player reporter, Player reported, String reason){
        final Report report = new Report(reporter, reported, reason);
        final UUID id = report.getId();

        reports.put(id, report);

        final Document reportDocument = new Document()
                .append("id", id)
                .append("reporter", report.getReporter().getName())
                .append("reported", report.getReported().getName())
                .append("reason", report.getReason())
                .append("timestamp", report.getTimestamp());

        reportsCollection.insertOne(reportDocument);
        return id;
    }

    public Optional<Report> getReport(UUID id){
        final Document reportDocument = new Document().append("id", id);
        final FindIterable<Report> documents = reportsCollection.find(reportDocument, Report.class);

        return Optional.ofNullable(documents.first());
    }

    public Optional<Set<Report>> all(int limit){
        final FindIterable<Report> documents = reportsCollection.find(Report.class);
        final HashSet<Report> reports = new HashSet<>();

        for (Report report : documents.limit(limit))
            reports.add(report);

        return Optional.of(reports);
    }

    public void deleteReport(UUID id){
        final Document reportDocument = new Document().append("id", id);

        reportsCollection.deleteOne(reportDocument);
    }
}
