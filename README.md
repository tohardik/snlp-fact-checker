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

The system reads the input file and creates a list of fact objects with factid and fact as its attributes. Then it performs POS taggin for all the facts and extracts Predicate, Object and Subject. Information related to the subject of each fact is then fetched from DBPedia using http call. Structered data is them processed to extract the information particular to the given predicate of the fact. Then we search for the presence of the object in the information extracted. Based on the similarity of the information fetched and the value given in the fact the system assigns a vale between -1.0 to +1.0 to the fact and writes the resuts into the result.ttl file.

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
