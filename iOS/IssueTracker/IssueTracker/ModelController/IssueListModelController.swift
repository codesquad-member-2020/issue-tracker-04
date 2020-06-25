import Foundation

class IssueListModelController: Observable {
    let user = User(id: FakeID.userId, name: "Foo")

    private let loader: IssueLoader
    var issueCollection: IssueCollection {
        didSet { notifyObservers() }
    }

    var observations = [ObjectIdentifier: Observation]()

    init(_ issueCollection: IssueCollection = [], loader: IssueLoader = .init()) {
        self.issueCollection = issueCollection
        self.loader = loader
    }

    func findIssue(by id: ID) -> BriefIssue? {
        issueCollection.first { $0.id == id }
    }

    func findIndex(by issueId: ID) -> IssueCollection.Index? {
        issueCollection.firstIndex { $0.id == issueId }
    }

    func updateIssue(with newIssue: BriefIssue) {
        guard let index = findIndex(by: newIssue.id) else { return }

        if issueCollection[index] != newIssue {
            issueCollection[index] = newIssue
        }
    }

    func addPartialIssue(_ partial: PartialIssue) {
        let issue = BriefIssue(id: FakeID.make(), title: partial.title, body: partial.body, owner: user.name)
        issueCollection.insert(newElement: issue, at: 0)
    }

    func convertToBriefIssue(from detail: Issue) -> BriefIssue {
        return BriefIssue(id: detail.id, title: detail.title, body: detail.body, owner: user.name)
    }

    func convertToDetailIssue(from brief: BriefIssue) -> Issue {
        let label = Label(id: 10, title: "iOS", color: "black")
        return Issue(id: brief.id, title: brief.title, body: brief.body, owner: user, labels: [label])
    }
}

extension IssueListModelController {
    static func createWithFakeData() -> IssueListModelController {
        let user = User(id: FakeID.userId, name: "모오오오스")
        let issues: IssueCollection = [
            BriefIssue(id: 1, title: "title1", body: nil, owner: user.name),
            BriefIssue(id: 2, title: "title2", body: "Something", owner: user.name),
            BriefIssue(id: 3, title: "title3", body: "Special", owner: user.name)
        ]

        return .init(issues)
    }
}

extension IssueListModelController {
    func fetchList(_ handler: @escaping APIHandler<IssueCollectionWrapper>) {
        loader.loadList(handler: handler)
    }
}
