import Foundation

class IssueModelController {
    private(set) var issue: Issue

    init(_ issue: Issue) {
        self.issue = issue
    }

    func update(issue: Issue) {
        self.issue = issue
    }
}
