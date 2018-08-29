package motifDiscovery;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import net.seninp.jmotif.sax.NumerosityReductionStrategy;
import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.SAXProcessor;
import net.seninp.jmotif.sax.TSProcessor;
import net.seninp.jmotif.sax.alphabet.Alphabet;
import net.seninp.jmotif.sax.alphabet.NormalAlphabet;
import net.seninp.jmotif.sax.datastructure.SAXRecord;
import net.seninp.jmotif.sax.datastructure.SAXRecords;

public class patternDiscovery {
	private static final String timeSeriesData = "/Users/Jia/Documents/workspaces/motifDiscovery/src/motifDiscovery/oneDimension.csv";
	private static final String CR = "\n";
	private static final String COMMA = ", ";

	public static void main(String[] args) throws IOException, SAXException{
		
		int WIN_SIZE=6;
		int PAA_SIZE=6;
		int ALPHABET_SIZE=3;
		double NORM_THRESHOLD=0.01;
		
		 // read the data
		double[] series = TSProcessor.readFileColumn(timeSeriesData, 0, 0);

		// instantiate classes
		Alphabet na = new NormalAlphabet();
		SAXProcessor sp = new SAXProcessor();

		// perform discretization
		SAXRecords saxData = sp.ts2saxViaWindow(series, WIN_SIZE, PAA_SIZE, na.getCuts(ALPHABET_SIZE),
				NumerosityReductionStrategy.NONE, NORM_THRESHOLD);
		


        ArrayList<Integer> indexes = new ArrayList<Integer>();
        indexes.addAll(saxData.getIndexes());
        Collections.sort(indexes);

        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/Users/Jia/Documents/workspaces/motifDiscovery/src/motifDiscovery/SaxByExc.csv")));
        for (Integer idx : indexes) {
          bw.write(idx + COMMA + String.valueOf(saxData.getByIndex(idx).getPayload()) + CR);
        }
        bw.close();

        // get the list of 10 most frequent SAX words
		ArrayList<SAXRecord> motifs = saxData.getMotifs(10);
		SAXRecord topMotif = motifs.get(0);

		    // print motifs
		System.out.println("top motif " + String.valueOf(topMotif.getPayload()) + " seen " + 
		          topMotif.getIndexes().size() + " times.");

	}

}
