//
//  ObservableGitHubUserListViewModel.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI
import shared
import Combine
import KMPNativeCoroutinesCombine
import KMPNativeCoroutinesAsync

@MainActor
class ObservableGitHubUserListViewModel: ObservableObject {
    var viewModel = GitHubUserListViewModel()

    @Published public var items: [GitHubUser] = []
    @Published public var isLoading = false
    @Published public var isFetchingFinished = false
    @Published public var error: String = ""
    var cancellables = Set<AnyCancellable>()

    init() {
        createPublisher(for: viewModel.isFetchingFinishedNative)
            .receive(on: DispatchQueue.main)
            .sink { _ in } receiveValue: { [weak self] value in
                self?.isFetchingFinished = value.boolValue
            }
            .store(in: &cancellables)
    }

    func activate() {
        Task {
            do {
                for try await value in asyncStream(for: viewModel.usersNative) {
                    self.items = value
                }
                for try await value in asyncStream(for: viewModel.isLoadingNative) {
                    self.isLoading = value.boolValue
                }
                for try await value in asyncStream(for: viewModel.errorNative) {
                    self.error = value
                }
            } catch {
                print("Failed with error: \(error)")
            }
        }
    }

    func deactivate() {
        viewModel.clear()
    }
}
