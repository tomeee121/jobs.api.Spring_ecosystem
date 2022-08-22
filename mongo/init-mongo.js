db.createUser(
    {
        user: "tomeee",
        pwd: "tomeee",
        roles: [
            {
                role: "readWrite",
                db: "offers"
            }
        ]
    }
)