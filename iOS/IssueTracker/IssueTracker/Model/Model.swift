import Foundation

typealias ID = Int

protocol Model: Hashable, Codable {
    var id: ID { get }
}
