import Foundation

struct Comment {
    let id: ID
    let body: String
    let author: User
}

typealias ID = Int

struct CommentCollection {
    typealias CommentType = [Comment]
    
    var comments: CommentType = .init()
    
    func contains(author: User) -> Bool {
        self.contains { $0.author == author }
    }
    
}

extension CommentCollection: Collection {
    
    typealias Index = CommentType.Index
    typealias Element = CommentType.Element
    
    var startIndex: Index { comments.startIndex }
    var endIndex: Index { comments.endIndex}
    
    subscript(position: Index) -> Element { comments[position] }
    
    func index(after i: Index) -> Index {
        comments.index(after: i)
    }
}

extension CommentCollection: ExpressibleByArrayLiteral {
    typealias ArrayLiteralElement = Element

    init(arrayLiteral elements: Element...) {
        comments = elements
    }
    
    func filter(_ isIncluded: (CommentType.Element) throws -> Bool) rethrows -> Self {
           return Self(comments: try comments.filter(isIncluded))
    }

}
