import Foundation

extension URLSession {
    @discardableResult
    func request(_ endpoint: Endpoint, then handler: @escaping (Result<Data, APIError>) -> Void) -> URLSessionTask {
        debugPrint(endpoint.url)
        let task = dataTask(with: endpoint.urlRequest) { (data, response, error) in
            guard let data = data else {
                return handler(.failure(.invalidData))
            }

            if let error = error {
                handler(.failure(.networkFailure(error)))
            }

            handler(.success(data))
        }
        task.resume()

        return task
    }
}
