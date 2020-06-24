enum Name {
    enum SystemImage {
        static let cellClose = "checkmark.rectangle"
        static let cellDelete = "trash"
    }

    enum Title {
        static let labelList = "Label"
        static let milestoneList = "Milestone"
    }
}

enum Identifier {
    enum Segue {
        static let save = "CreatedSegue"
        static let issueDetail = "IssueDetailSegue"
    }

    enum Cell {
        static let issue = String(describing: IssueCell.self)
        static let comment = String(describing: CommentCell.self)
        static let label = String(describing: LabelCollectionViewCell.self)
    }

    enum ViewController {
        static let list = String(describing: BasicListViewController.self)
        static let issueFilter = String(describing: IssueFilterViewController.self)
        static let assigneeCellVC = String(describing: AssigneeCellViewController.self)
        static let labelCellVC = String(describing: LabelCellViewController.self)
        static let milestoneCellVC = String(describing: MilestoneCellViewController.self)
        static let basicForm = String(describing: BasicFormViewController.self)
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
