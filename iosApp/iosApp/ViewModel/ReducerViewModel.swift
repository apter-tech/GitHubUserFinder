//
//  GitHubUserListViewModelImpl.swift
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
class ReducerViewModel<S: UiState, V: ViewModel>: ObservableObject {
    var viewModel: V

    @Published public var state: S?
    var cancellables = Set<AnyCancellable>()

    init(viewModel: V) {
        self.viewModel = viewModel

        guard let reducer = (viewModel as? Reducer<S, UiEvent>) else { return }
        createPublisher(for: reducer.stateNative)
            .receive(on: DispatchQueue.main)
            .sink { _ in } receiveValue: { [weak self] value in
                self?.state = value
            }
            .store(in: &cancellables)
    }

    func deactivate() {
        viewModel.clear()
        cancellables.removeAll()
    }
}
