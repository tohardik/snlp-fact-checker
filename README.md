# SNLP FACT CHECKER - (WS2018-19)

### Team Name: SNLP1234
<br/>

### Members:

* Hardik Topiwala - 6856355
* Nandeesh Prabushanker - 6855882
<br/>

### Problem Statement:

   Given a TSV file with fact ids and facts from Wikipedia, Generate a TTL file with truth values between -1.0 to +1.0 for all the given facts(-1.0 being false and +1.0 being true).
<br/>

### Description of the approach

The system reads the input file and creates a list of fact objects with factid and fact as its attributes. Then, for each fact, RDF Triplets are extracted with Predicate, Object and Subject. Information related to the subject of each fact is then fetched from DBPedia REST API. The response from DBPedia REST API is processed to find the relevant data particular to the predicate type. Based on the similarity of the information fetched from DBPedia and the value given in the fact, the system assigns a value between -1.0 to +1.0 to the fact and writes the resuts into the result.ttl file.

#### Example:

Fact: **3820003	Interstellar (movie) stars Anne Hathaway.**

Triplets formed using the given fact would be
```
Subject:   Interstellar (movie)
Object:    Anne Hathaway
Predicate: stars
```

URL to get the infromation from DBPedia: http://dbpedia.org/data/Interstellar_(film).json and part of the response having the interested information looks like,

```
http://dbpedia.org/ontology/starring: [
	{
		type: "uri",
		value: "http://dbpedia.org/resource/Bill_Irwin"
	},
	{
		type: "uri",
		value: "http://dbpedia.org/resource/Michael_Caine"
	},
	{
		type: "uri",
		value: "http://dbpedia.org/resource/Matthew_McConaughey"
	},
	{
		type: "uri",
		value: "http://dbpedia.org/resource/Anne_Hathaway"
	},
	{
		type: "uri",
		value: "http://dbpedia.org/resource/Ellen_Burstyn"
	},
	{
		type: "uri",
		value: "http://dbpedia.org/resource/Jessica_Chastain"
	}
],
```

The system parses the JSON received as the response and extracts the list of values of **Starring** property and searches for the value **Anne Hathaway** in the list. The list has a value same as the object of the fact and hence the truth fullness value of the fact is set to +1.0 and the result it written onto the file. The result file will have the below value for the example fact,

```
<http://swc2017.aksw.org/task2/dataset/3820003><http://swc2017.aksw.org/hasTruthValue>"1.0"^^<http://www.w3.org/2001/XMLSchema#double>.
```

### Steps to build and run the application.
The project uses Maven to manage dependencies.
1. Check out the project / Download the source code.
2. Import the project as a Maven Project in your preferred IDE(Or download IntelliJ: https://www.jetbrains.com/idea/download/#section=windows).
3. Build the project.
4. Execute App.main(). This takes about 2 minutes to process all the facts inside the test.tsv file.
5. A file named result.ttl is generated under the directory src/main/resources. 


### List of facts which are wrongly categorized by the system
### False Positive
Cristiano Ronaldo's current team is Real Madrid.
Narendra Modi's role is Chief Minister of Gujarat.
Michael Keaton's award is Academy Award for Best Actor.
Liverpool F.C. is Luis Suárez's current squad.
Golden Globe is Liam Neeson's honour.

### False Negative
Los Angeles Lakers is LeBron James' squad.
DataFox is Oracle Corporation's subsidiary.
Interstellar (film) stars Casey Affleck.
Barack Obama's award is Profile in Courage Award.
Virat Kohli's birth place is Delhi, India.
