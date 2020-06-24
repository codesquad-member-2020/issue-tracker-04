import Foundation

enum APIError: Error {
    case invalidData
    case networkFailure(Error)
    case decodeFailure(Error)

}

protocol APILoader {
    typealias Handler<T> = (Result<T, APIError>) -> Void

    func load<T>(using session: URLSession, endpoint: Endpoint, handler: @escaping Handler<T>) where T: Decodable
    func parse<T>(data: Data) throws -> T where T: Decodable
}

extension APILoader {
    func load<T>(using session: URLSession, endpoint: Endpoint, handler: @escaping Handler<T>) where T: Decodable {
        session.request(endpoint) { result in
            switch result {
            case .success(let data):
                do {
                    let decoded: T = try self.parse(data: data)
                    handler(.success(decoded))
                } catch {
                    handler(.failure(.decodeFailure(error)))
                    debugPrint(error)
                }
            case .failure(let error):
                debugPrint(error)
                handler(.failure(error))
            }
        }
    }

    func parse<T>(data: Data) throws -> T where T: Decodable {
        return try JSONDecoder().decode(T.self, from: data)
    }
}
