enum SystemImageName {
    static let cellClose = "checkmark.rectangle"
    static let cellDelete = "trash"
}

enum Identifier {
    enum Segue {
        static let save = "CreatedSegue"
        static let issueDetail = "IssueDetailSegue"
    }
}

struct FakeID {
    static func make() -> ID {
        return Int.random(in: 1...10_0000)
    }

    static let userId = 1
}
