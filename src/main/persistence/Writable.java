package persistence;

import org.json.JSONObject;

/***************************************************************************************
 *    An interface in JsonSerializationDemo
 *    Title: JsonSerializationDemo - Simple application to illustrate JSON serialization
 *    Author: CPSC210
 *    Date: n/a
 *    Code version: n/a
 *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 *
 ***************************************************************************************/
public interface Writable {
    //EFFECTS: return this as JSON object
    JSONObject toJson();
}
