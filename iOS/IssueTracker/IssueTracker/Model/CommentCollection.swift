import Foundation

struct CommentCollection {
    typealias CommentType = [Comment]

    private var elements: CommentType = .init()

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
