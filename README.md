# My Smoking Log (나만의 흡연 기록)

**My Smoking Log**는 사용자가 자신의 흡연 습관을 기록하고, 시각화된 통계를 통해 흡연량을 관리할 수 있도록 돕는 안드로이드 애플리케이션입니다. 최신 안드로이드 기술 스택인 **Jetpack Compose**와 **Clean Architecture**를 기반으로 개발되었습니다.

## 📱 주요 기능 (Features)

### 1. 홈 (Home)
- **실시간 기록**: 버튼 클릭 한 번으로 흡연 시간을 기록합니다.
- **타이머**: 마지막 흡연으로부터 경과한 시간을 실시간으로 보여줍니다 (예: "2시간 30분 지남").
- **일일 현황**: 오늘의 흡연 개수와 설정된 일일 제한량을 비교하여 보여줍니다.

### 2. 통계 (Statistics)
**데이터 시각화와 분석을 통해 흡연 패턴을 파악합니다.**
- **주간 흡연량**: 지난 7일간의 흡연량을 막대 그래프(Bar Chart)로 시각화합니다.
- **오늘의 분포**: 하루 24시간 중 언제 흡연했는지를 점 그래프(Dot Chart)로 보여줍니다.
- **월간 리포트**: 이번 달 총 흡연 개수와 예상 비용을 계산합니다.
- **스트릭(Streak)**: 현재 금연 지속 일수와 역대 최장 금연 시간 기록을 추적합니다.
- **반응형 업데이트**: 홈 화면에서 기록을 추가하거나 설정을 변경하면 통계 화면에 즉시 반영됩니다.

### 3. 설정 (Settings)
- **일일 목표**: 하루 최대 흡연 개수 목표를 설정합니다.
- **담배 가격**: 한 갑당 가격을 설정하여 월간 비용을 정확히 계산합니다.

---

## 🛠 기술 스택 (Tech Stack)
- **Language**: Kotlin
- **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetbrains/compose) (Material3 Design)
- **Architecture**: Clean Architecture (Presentation - Domain - Data) + MVVM Pattern
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
- **Asynchronous**: Coroutines & [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
- **Local Database**: [Room](https://developer.android.com/training/data-storage/room)

---

## 🏗 아키텍처 (Architecture)

**Clean Architecture** 원칙을 적용하여 관심사를 분리했습니다.

### Layer 구분
1.  **Presentation Layer**
    -   `UI`: Composable 함수들로 구성된 화면 (`StatScreen`, `HomeScreen`).
    -   `ViewModel`: `GetStatUseCase` 등 UseCase를 통해 데이터를 수집하고, `StateFlow`를 통해 UI 상태(`StatState`)를 관리합니다.
2.  **Domain Layer**
    -   `UseCase`: 비즈니스 로직을 담당합니다. (예: `GetStatUseCase`는 Repository에서 데이터를 받아 통계를 계산).
    -   `Model`: 순수 Kotlin 데이터 클래스 (`Smoking`, `UserSetting`).
    -   `Repository Interface`: 데이터 계층과의 의존성 역전을 위한 인터페이스.
3.  **Data Layer**
    -   `Repository Impl`: Interface의 구현체. Room DAO와 Mapper를 사용해 데이터를 처리합니다.
    -   `Room DB`: 로컬 데이터베이스 (`SmokingDao`, `UserSettingsDao`).
    -   `Mapper`: Entity와 Domain Model 간의 변환을 담당.

---
