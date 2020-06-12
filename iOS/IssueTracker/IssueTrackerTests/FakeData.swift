import Foundation
@testable import IssueTracker

class Faker {
    static func makeIssues() -> IssueCollection {
        let user = makeUser()
        let anotherUser = makeAnotherUser()
        let issueList: IssueCollection = [
            Issue(id: 1, title: "title1", body: nil, owner: user),
            Issue(id: 2, title: "title2", body: "Something", owner: anotherUser, status: .closed),
            Issue(id: 3, title: "title3", body: "Special", owner: user)
        ]

        return issueList
    }

    static func makeUser() -> User {
        let user = User(id: 1, name: "모오오오스")
        return user
    }

    static func makeAnotherUser() -> User {
        let user = User(id: 2, name: "Lenaaaaaaa")
        return user
    }

    static func makeIssue() -> Issue {
        let user = makeUser()
        let issue = Issue(id: 1, title: "title1", body: nil, owner: user)

        return issue
    }

    static func makeComments() -> CommentCollection {
        let user = makeUser()
        let anotherUser = makeAnotherUser()

        let comments: CommentCollection = [
            Comment(id: 1, body: "comment1", author: user),
            Comment(id: 2, body: "comment2", author: anotherUser),
            Comment(id: 3, body: "comment3", author: anotherUser),
        ]

        return comments
    }
}
