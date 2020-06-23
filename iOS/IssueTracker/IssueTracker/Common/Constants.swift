enum SystemImageName {
    static let cellClose = "checkmark.rectangle"
    static let cellDelete = "trash"
}

enum Identifier {
    enum Segue {
        static let save = "CreatedSegue"
        static let issueDetail = "IssueDetailSegue"
    }

    enum Cell {
        static let issue = String(describing: IssueCell.self)
        static let comment = String(describing: CommentCell.self)
        static let assignee = String(describing: AssigneeCell.self)
    }

    enum ViewController {
        static let list = String(describing: ListViewController.self)
        static let issueFilter = String(describing: IssueFilterViewController.self)
    }
}

struct FakeID {
    static func make() -> ID {
        return Int.random(in: 1...10_0000)
    }

    static let userId = 1
}
