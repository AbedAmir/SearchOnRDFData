package uvsq;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;

import javax.management.Query;

import org.apache.jena.iri.impl.Main;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.riot.RDFParser;
import org.apache.jena.riot.system.ErrorHandlerFactory;
import org.apache.jena.util.FileManager;

import com.github.jsonldjava.core.RDFDataset.Literal;

public class ClassMetier {
	String mots [];
	String motss [];
	public void transformTurtle2Triple(String nomFichier)
	{
		Model model = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(nomFichier);
		if (in == null) {
			System.out.println("impossible de trouver le fichier");
		}
		model.read(in,null);
		//model.write(System.out);
		StmtIterator iter = model.listStatements();

		// print out the predicate, subject and object of each statement
		while (iter.hasNext()) {
			Statement stmt      = iter.nextStatement();  // get next statement
			Resource  subject   = stmt.getSubject();     // get the subject
			Property  predicate = stmt.getPredicate();   // get the predicate
			RDFNode   object    = stmt.getObject();      // get the object "Literal ou object"

			System.out.print(subject.toString());
			System.out.print(" " + predicate.toString() + " ");
			if (object instanceof Resource) {
				System.out.print(object.toString());
			} else {
				// object is a literal
				System.out.print(" \"" + object.toString() + "\"");
			}

			System.out.println(" .");
		}
	}

	public void ecritureNouveauTriplet()
	{
		Model monModele = ModelFactory.createDefaultModel();
		// Créer une ressource MESSAGE
		Resource message = monModele.createResource("http://webtutoriel/message");
		// Créer une propriété VALEUR
		Property valeurProp = monModele.createProperty("http://webtutoriel/valeur");
		// Assigner une valeur à la ressource
		message.addProperty(valeurProp, "Bonjour à tous");
		// Imprimer le contenu du modèle dans la syntaxe TTL 
		monModele.write( System.out , "TTL");
	}

	public void lectureGrapheParser() throws FileNotFoundException, IOException
	{
		try (InputStream in = new FileInputStream("1997.ttl")) {
			RDFParser.create()
			.source(in)
			.lang(RDFLanguages.NTRIPLES)
			.errorHandler(ErrorHandlerFactory.errorHandlerStrict)
			.base("http://example/base");
			//.parse(noWhere);
		}
	}

	public void sparqlTest()
	{
		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model model = FileManager.get().loadModel("97.rdf");

		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+"PREFIX db: <http://localhost:2020/>"
				+"PREFIX baseP: <http://localhost:2020/produit/>"
				+"PREFIX baseV: <http://localhost:2020/vente/>"
				+"PREFIX baseC: <http://localhost:2020/client/>"
				+"SELECT ?subject ?predicate ?object WHERE {"
				+ "?subject ?predicate baseC:1 .} ";
		org.apache.jena.query.Query query = QueryFactory.create(queryString);
		QueryExecution queryExecution = QueryExecutionFactory.create(query,model);
		try 
		{
			org.apache.jena.query.ResultSet resultSet = queryExecution.execSelect();

			while(resultSet.hasNext())
			{
				QuerySolution querySolution = resultSet.nextSolution();
				//Resource name =  querySolution.getResource("subject = <http://localhost:2020/vocab/client>");
				//System.out.println(name);
				System.out.println(querySolution);
			}
		} finally {
			queryExecution.close();
		}

	}

	public void sparqlTestAll()
	{
		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model model = FileManager.get().loadModel("97.rdf");

		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+"PREFIX db: <http://localhost:2020/>"
				+"PREFIX baseP: <http://localhost:2020/produit/>"
				+"PREFIX baseV: <http://localhost:2020/vente/>"
				+"PREFIX baseC: <http://localhost:2020/client/>"
				+"SELECT ?subject ?predicate ?object WHERE {"
				+ "?subject ?predicate ?object .}";
		org.apache.jena.query.Query query = QueryFactory.create(queryString);
		QueryExecution queryExecution = QueryExecutionFactory.create(query,model);
		try 
		{
			org.apache.jena.query.ResultSet resultSet = queryExecution.execSelect();

			while(resultSet.hasNext())
			{
				QuerySolution querySolution = resultSet.nextSolution();
				//Resource name =  querySolution.getResource("subject = <http://localhost:2020/vocab/client>");
				//System.out.println(name);
				System.out.println(querySolution);
			}
		} finally {
			queryExecution.close();
		}

	}

	public void sparqlTestRegexDebutPhrase()
	{
		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model model = FileManager.get().loadModel("97.rdf");

		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+"PREFIX db: <http://localhost:2020/>"
				+"PREFIX baseP: <http://localhost:2020/produit/>"
				+"PREFIX baseV: <http://localhost:2020/vente/>"
				+"PREFIX baseC: <http://localhost:2020/client/>"
				+"SELECT ?subject ?predicate ?object WHERE {"
				+ "?subject ?predicate ?object "
				+ "FILTER regex (str(?subject),\"^http://localhost:2020/client/1\")}";
		org.apache.jena.query.Query query = QueryFactory.create(queryString);
		QueryExecution queryExecution = QueryExecutionFactory.create(query,model);
		try 
		{
			org.apache.jena.query.ResultSet resultSet = queryExecution.execSelect();

			while(resultSet.hasNext())
			{
				QuerySolution querySolution = resultSet.nextSolution();
				//Resource name =  querySolution.getResource("subject = <http://localhost:2020/vocab/client>");
				//System.out.println(name);
				System.out.println(querySolution);
			}
		} finally {
			queryExecution.close();
		}

	}

	public void sparqlTestRegexContientMot()
	{
		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model model = FileManager.get().loadModel("data.rdf");

		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+"PREFIX db: <http://localhost:2020/>"
				+"PREFIX baseP: <http://localhost:2020/produit/>"
				+"PREFIX baseV: <http://localhost:2020/vente/>"
				+"PREFIX baseC: <http://localhost:2020/client/>"
				+"SELECT ?subject ?predicate ?object WHERE {"
				+ "?subject ?predicate ?object "
				+ "FILTER regex (str(?subject),\".\")}";
		org.apache.jena.query.Query query = QueryFactory.create(queryString);
		QueryExecution queryExecution = QueryExecutionFactory.create(query,model);
		try 
		{
			org.apache.jena.query.ResultSet resultSet = queryExecution.execSelect();

			while(resultSet.hasNext())
			{
				QuerySolution querySolution = resultSet.nextSolution();
				//Resource name =  querySolution.getResource("subject = <http://localhost:2020/vocab/client>");
				//System.out.println(name);
				System.out.println(querySolution);
			}
		} finally {
			queryExecution.close();
		}

	}

	public void sparqlTestRegexContientMotSelect(String mot, String metier)
	{
		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model model = FileManager.get().loadModel("data.rdf");

		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+"PREFIX db: <http://localhost:2020/>"
				+"PREFIX baseCategorie: <http://localhost:2020/categorie>"
				+"PREFIX baseMetier: <http://localhost:2020/metiers//>"
				+"PREFIX baseProfessions: <http://localhost:2020/professions/>"
				+"PREFIX baseGroupe: <http://localhost:2020/groupe/>"
				+"SELECT ?subject ?predicate ?object WHERE {"
				+ "?subject ?predicate ?object "
				+ "FILTER (regex (str(?object),\""+mot+"\",\"i\") && regex (str(?subject),\""+metier+"\",\"i\"))}";
		org.apache.jena.query.Query query = QueryFactory.create(queryString);
		QueryExecution queryExecution = QueryExecutionFactory.create(query,model);
		try 
		{
			org.apache.jena.query.ResultSet resultSet = queryExecution.execSelect();

			while(resultSet.hasNext())
			{
				QuerySolution querySolution = resultSet.nextSolution();
				//Resource name =  querySolution.getResource("subject = <http://localhost:2020/vocab/client>");
				//System.out.println(name);
				System.out.println(querySolution);
			}
		} finally {
			queryExecution.close();
		}

	}

	public void rechercheMetier(String mot)
	{
		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model model = FileManager.get().loadModel("data.rdf");

		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+"PREFIX db: <http://localhost:2020/>"
				+"PREFIX baseCategorie: <http://localhost:2020/categorie>"
				+"PREFIX baseMetier: <http://localhost:2020/metiers//>"
				+"PREFIX baseProfessions: <http://localhost:2020/professions/>"
				+"PREFIX baseGroupe: <http://localhost:2020/groupe/>"
				+"SELECT ?subject ?predicate ?object WHERE {"
				+ "?subject ?predicate ?object "
				+ "FILTER (regex (str(?object),\""+mot+"\",\"i\") && regex (str(?subject),\""+"metier"+"\",\"i\"))}";
		org.apache.jena.query.Query query = QueryFactory.create(queryString);
		QueryExecution queryExecution = QueryExecutionFactory.create(query,model);
		try 
		{
			org.apache.jena.query.ResultSet resultSet = queryExecution.execSelect();

			while(resultSet.hasNext())
			{
				QuerySolution querySolution = resultSet.nextSolution();
				mots = querySolution.toString().split("\" ");
				motss = mots[0].split("=");
				System.out.println(motss[1] + "\"");
			}
		} finally {
			queryExecution.close();
		}

	}

	public void rechercheGroupe(String mot)
	{
		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model model = FileManager.get().loadModel("data.rdf");

		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+"PREFIX db: <http://localhost:2020/>"
				+"PREFIX baseCategorie: <http://localhost:2020/categorie>"
				+"PREFIX baseMetier: <http://localhost:2020/metiers//>"
				+"PREFIX baseProfessions: <http://localhost:2020/professions/>"
				+"PREFIX baseGroupe: <http://localhost:2020/groupe/>"
				+"SELECT ?subject ?predicate ?object WHERE {"
				+ "?subject ?predicate ?object "
				+ "FILTER (regex (str(?object),\""+mot+"\",\"i\") && regex (str(?subject),\""+"groupe"+"\",\"i\"))}";
		org.apache.jena.query.Query query = QueryFactory.create(queryString);
		QueryExecution queryExecution = QueryExecutionFactory.create(query,model);
		try 
		{
			org.apache.jena.query.ResultSet resultSet = queryExecution.execSelect();

			while(resultSet.hasNext())
			{
				QuerySolution querySolution = resultSet.nextSolution();
				mots = querySolution.toString().split("\" ");
				motss = mots[0].split("=");
				System.out.println(motss[1] + "\"");
			}
		} finally {
			queryExecution.close();
		}

	}

	public void rechercheProfession(String mot)
	{
		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model model = FileManager.get().loadModel("data.rdf");

		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+"PREFIX db: <http://localhost:2020/>"
				+"PREFIX baseCategorie: <http://localhost:2020/categorie>"
				+"PREFIX baseMetier: <http://localhost:2020/metiers//>"
				+"PREFIX baseProfessions: <http://localhost:2020/professions/>"
				+"PREFIX baseGroupe: <http://localhost:2020/groupe/>"
				+"SELECT ?subject ?predicate ?object WHERE {"
				+ "?subject ?predicate ?object "
				+ "FILTER (regex (str(?object),\""+mot+"\",\"i\") && regex (str(?subject),\""+"professions"+"\",\"i\"))}";
		org.apache.jena.query.Query query = QueryFactory.create(queryString);
		QueryExecution queryExecution = QueryExecutionFactory.create(query,model);
		try 
		{
			org.apache.jena.query.ResultSet resultSet = queryExecution.execSelect();

			while(resultSet.hasNext())
			{
				QuerySolution querySolution = resultSet.nextSolution();
				mots = querySolution.toString().split("\" ");
				motss = mots[0].split("=");
				System.out.println(motss[1] + "\"");
			}
		} finally {
			queryExecution.close();
		}

	}

	public void rechercheCategorie(String mot)
	{
		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model model = FileManager.get().loadModel("data.rdf");

		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+"PREFIX db: <http://localhost:2020/>"
				+"PREFIX baseCategorie: <http://localhost:2020/categorie>"
				+"PREFIX baseMetier: <http://localhost:2020/metiers//>"
				+"PREFIX baseProfessions: <http://localhost:2020/professions/>"
				+"PREFIX baseGroupe: <http://localhost:2020/groupe/>"
				+"SELECT ?subject ?predicate ?object WHERE {"
				+ "?subject ?predicate ?object "
				+ "FILTER (regex (str(?object),\""+mot+"\",\"i\") && regex (str(?subject),\""+"categorie"+"\",\"i\"))}";
		org.apache.jena.query.Query query = QueryFactory.create(queryString);
		QueryExecution queryExecution = QueryExecutionFactory.create(query,model);
		try 
		{
			org.apache.jena.query.ResultSet resultSet = queryExecution.execSelect();

			while(resultSet.hasNext())
			{
				QuerySolution querySolution = resultSet.nextSolution();
				mots = querySolution.toString().split("\" ");
				motss = mots[0].split("=");
				System.out.println(motss[1] + "\"");
			}
		} finally {
			queryExecution.close();
		}

	}

}
