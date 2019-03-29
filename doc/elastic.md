
{
  "query": {
    "bool": {
      "must": [
        {
          "range":{
            "inventory":{"gt": 3}
          }
        },
        {
          "term": {
            "specialOrder": 1
          }
        }
      ]
    }
  },
  "_source" : {
            "excludes" : ["shortDescription","description"]
    }
}