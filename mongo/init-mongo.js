// db.auth('root', 'example')

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

// conn = new Mongo();
// db = conn.getDB("offers");


// db.offer1.createIndex({ "id": 1 }, { unique: false });
// db.offer1.insert({"title": "Software Engineer - Mobile (m/f/d)", "salary": "4k - 8k PLN", "company": "Cybersource", "offerUrl": "https://nofluffjobs.com/pl/job/software-engineer-mobile-m-f-d-cybersource-poznan-entavdpn"});
//
// db.offer1.createIndex({ "id": 2 }, { unique: false });
// db.offer1.insert({"title": "Junior DevOps Engineer", "salary": "8k - 14k PLN", "company": "CDQ Poland", "offerUrl": "https://nofluffjobs.com/pl/job/junior-devops-engineer-cdq-poland-wroclaw-gnymtxqd"});