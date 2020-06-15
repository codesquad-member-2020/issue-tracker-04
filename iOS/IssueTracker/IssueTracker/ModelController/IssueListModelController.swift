import Foundation

class IssueListModelController {
    private(set) var issueCollection: IssueCollection

    init(_ issueCollection: IssueCollection) {
        self.issueCollection = issueCollection
    }
}

extension IssueListModelController {
    static func createFakeData() -> IssueCollection {
        let user = User(id: 1, name: "모오오오스")

        return [Issue(id: 1, title: "title1", body: nil, owner: user),Issue(id: 2, title: "title2", body: "Something", owner: user),Issue(id: 3, title: "title3", body: "Special", owner: user)]
    }
}
