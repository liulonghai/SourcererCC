package indexbased;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import models.Bag;

import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class indexMerger {
    private List<FSDirectory> invertedIndexDirectories;
    private List<FSDirectory> forwardIndexDirectories;

    public indexMerger() {
        super();
        this.invertedIndexDirectories = new ArrayList<FSDirectory>();
        this.forwardIndexDirectories = new ArrayList<FSDirectory>();
    }

    private void populateIndeXdirs(String inputFile) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    inputFile), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null && line.trim().length() > 0) {
                String invertedIndexDirPath = line+"/index";
                String forwardIndexDirPath = line+"/fwd/index";
                FSDirectory idir = FSDirectory.open(new File(invertedIndexDirPath));
                this.invertedIndexDirectories.add(idir);
                FSDirectory fdir = FSDirectory.open(new File(forwardIndexDirPath));
                this.forwardIndexDirectories.add(fdir);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void mergeindexes() {
        // TODO Auto-generated method stub
        WhitespaceAnalyzer whitespaceAnalyzer = new WhitespaceAnalyzer(
                Version.LUCENE_46);
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
                Version.LUCENE_46, whitespaceAnalyzer);
        indexWriterConfig.setOpenMode(OpenMode.CREATE);
        IndexWriter indexWriter = null;
        try {
            FSDirectory dir = FSDirectory.open(new File("master/index"));
            indexWriter = new IndexWriter(dir, indexWriterConfig);
            FSDirectory[] dirs = this.invertedIndexDirectories
                    .toArray(new FSDirectory[this.invertedIndexDirectories
                            .size()]);
            indexWriter.addIndexes(dirs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                indexWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        KeywordAnalyzer keywordAnalyzer = new KeywordAnalyzer();
        IndexWriterConfig fwdIndexWriterConfig = new IndexWriterConfig(
                Version.LUCENE_46, keywordAnalyzer);
        fwdIndexWriterConfig.setOpenMode(OpenMode.CREATE);
        try {

            FSDirectory dir = FSDirectory.open(new File("master/fwd/index"));
            indexWriter = new IndexWriter(dir, fwdIndexWriterConfig);
            FSDirectory[] dirs = this.forwardIndexDirectories
                    .toArray(new FSDirectory[this.forwardIndexDirectories
                            .size()]);
            indexWriter.addIndexes(dirs);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                indexWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
