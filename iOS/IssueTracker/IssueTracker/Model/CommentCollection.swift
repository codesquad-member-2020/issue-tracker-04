import Foundation

struct CommentCollection {
    typealias CommentType = [Comment]

    private var elements: CommentType = .init()

    // MARK: - Mutating

    mutating func add(_ element: Element) {
        elements.append(element)
    }

    // MARK: - Filter

    func contains(author: User) -> Bool {
        self.contains { $0.author == author }
    }

    func filter(_ isIncluded: (CommentType.Element) throws -> Bool) rethrows -> Self {
        return Self(elements: try elements.filter(isIncluded))
    }

}

extension CommentCollection: Collection {

    typealias Index = CommentType.Index
    typealias Element = CommentType.Element

    var startIndex: Index { elements.startIndex }
    var endIndex: Index { elements.endIndex}

    subscript(position: Index) -> Element { elements[position] }

    func index(after i: Index) -> Index {
        elements.index(after: i)
    }
}

extension CommentCollection: ExpressibleByArrayLiteral {
    typealias ArrayLiteralElement = Element

    init(arrayLiteral elements: Element...) {
        self.elements = elements
    }

}

extension CommentCollection: Hashable { }

extension CommentCollection {
    static func makeFake() -> CommentCollection {
        let user = User(id: FakeID.userId, name: "ffff")
        let comments: CommentCollection = [Comment(id: FakeID.make(), body: "eee", author: user)]

        return comments
    }
}
