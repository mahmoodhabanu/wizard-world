

https://github.com/user-attachments/assets/20c128a1-bbac-481c-bfb3-0add3a510070

# wizard-world

This project showcases a clean architecture implementation for fetching a list of "Houses" (e.g., from the Wizarding World) using Kotlin, Coroutines, and a custom `Result` sealed class for explicit state management and robust error handling.
Public API used here is "https://wizard-world-api.herokuapp.com/Houses"

## Technologies Used

*   **Kotlin**: The primary programming language.
*   **Kotlin Coroutines**: For asynchronous operations.
*   **Retrofit**: (Implied by `HttpException` and network calls) For making type-safe HTTP requests to a REST API.
*   **Dagger Hilt**: (Implied by `@Inject` annotations) For dependency injection, ensuring components are easily testable and managed.
*   **MockK**: A powerful mocking library for Kotlin, used extensively in unit tests.
*   **Clean Architecture**: A well-established architectural pattern promoting separation of concerns into Domain, Data, and Presentation layers.
*   **Custom `Result` Sealed Class**: For explicit state management (Success, Error, Loading) of asynchronous operations, enhancing readability and predictability.
*   **Custom `AppError` Sealed Class**: For structured and categorized error handling, allowing for specific error responses.

## Architecture Overview

The project adheres to a clean architecture pattern, organizing code into distinct layers to promote maintainability, scalability, and testability:

*   **Domain Layer(houses-domain)**: This is the core of the application, containing business logic, entities (`House`), and use cases (`GetHousesUseCase`). It defines contracts (`HouseRepository` interface) that the data layer must implement. It is independent of any specific framework or technology.
*   **Data Layer(houses-data)**: Responsible for implementing the interfaces defined in the Domain Layer. It handles data retrieval from various sources (e.g., network via `HouseRemoteDataSource, local store for offline caching`).
*   **Presentation Layer(houses-presentation)** : This layer (e.g., an Android `ViewModel` and UI components(Compose is used here) would consume the `GetHousesUseCase` and react to the `Result` states (Success, Error) to update the user interface accordingly.

## Potential Improvements
* Centralized Network Availability Check and Offline Support and Caching
* Pagination for Large Dataset


## Setup
AGP version: 8.7.2
Android Studio Version : Android Studio Ladybug | 2024.2.1 Patch 2

<img width="1084" height="2412" alt="HousesListScreen" src="https://github.com/user-attachments/assets/06b1757c-0903-43f9-a8e7-ee413bc55435" />
<img width="1084" height="2412" alt="HousesDetailScreen" src="https://github.com/user-attachments/assets/8a7e2554-d94a-4bb3-9633-7f9db46abcdf" />

