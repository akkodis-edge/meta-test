# Grasshopper requires sqlite3_unlock_notify API
CFLAGS_append = " -DSQLITE_ENABLE_UNLOCK_NOTIFY"
