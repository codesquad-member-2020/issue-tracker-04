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
    }

    enum ViewController {
        static let list = String(describing: ListViewController.self)
        static let issueFilter = String(describing: IssueFilterViewController.self)
    }

    enum Storyboard {
        static let main = "Main"
    }
}

struct FakeID {
    static func make() -> ID {
        return Int.random(in: 1...10_0000)
    }

    static let userId = 1
}
