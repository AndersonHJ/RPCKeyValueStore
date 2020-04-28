namespace java keyvaluestore



service KeyValueStoreService
{
    string get(1:string key),

    bool put(1:string key, 2:string value),

    bool deleteKey(1:string key),
}
