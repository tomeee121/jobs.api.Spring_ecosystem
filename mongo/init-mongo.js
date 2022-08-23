db.createUser(
    {
        user: "tomeee",
        pwd: "pass",
        roles: [
            {
                role: "readWrite",
                db: "offers"
            }
        ]
    }
)