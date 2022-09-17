//
//  GitHubUserDetailsViewModelImpl.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI
import shared
import KMPNativeCoroutinesAsync

@MainActor
class GitHubUserDetailsViewModelImpl: ObservableObject {
    var viewModel = GitHubUserDetailsViewModel()

    @Published public var userDetails: GitHubUser?

    func activate() {
        Task {
            do {
                for try await value in asyncStream(for: viewModel.userDetailsNative) {
                    self.userDetails = value
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
