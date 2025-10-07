# Pensamiento Computacional API Documentation

## Setup and Running

### Running the Application:

```sql
mvn clean spring-boot:run
```

> If `mvn clean spring-boot:run` doesn't work:
> - Delete the `target` folder
> - Reinstall the `target`

```sql
mvn clean install
```

- Compile it

```sql
mvn clean compile
```

- Run it

```sql
mvn spring-boot:run
```

## Configuration

- **Application Name:** pensamiento_computacional
- **Server Port:** 8089
- **Server IP:** 192.168.131.107
- **Context Path:** /pcomputacional
- **Database:** H2 (in-memory)
- **H2 Console:** http://192.168.131.107:8089/pcomputacional/h2-console

## Demo accounts

The seeded users in `data.sql` authenticate with their institutional email and the following plain-text passwords:

| Email | Password |
| --- | --- |
| `user1@icesi.edu.co` | `user1pass` |
| `user2@icesi.edu.co` | `user2pass` |
| `user3@icesi.edu.co` | `user3pass` |
| `user4@icesi.edu.co` | `user4pass` |
| `user5@icesi.edu.co` | `user5pass` |

These values correspond to the BCrypt digests stored in `pensamiento-computacional/src/main/resources/data.sql`.

## API Endpoints

**Base URL:** `http://192.168.131.107:8089/pcomputacional`

---

### Academic Terms

#### Get Academic Term by Code
- **URL:** `GET /academic-terms/by-code/{termCode}`
- **Description:** Retrieve a specific academic term by its code
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/academic-terms/by-code/2025A
  ```
- **Response:** Single AcademicTerm object

#### Get Active Academic Terms
- **URL:** `GET /academic-terms/active`
- **Description:** Get all currently active academic terms
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/academic-terms/active
  ```
- **Response:** List of active AcademicTerm objects

---

### Activities

#### Get Activities by Group Section
- **URL:** `GET /activities/by-group-section?groupSectionId={id}`
- **Description:** Get all activities for a specific group section
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/activities/by-group-section?groupSectionId=123
  ```
- **Response:** List of Activity objects

#### Get Activities by Created By
- **URL:** `GET /activities/by-created-by?userId={id}`
- **Description:** Get all activities created by a specific user
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/activities/by-created-by?userId=456
  ```
- **Response:** List of Activity objects

#### Get Activities by Academic Term
- **URL:** `GET /activities/by-academic-term?academicTermId={id}`
- **Description:** Get all activities for a specific academic term
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/activities/by-academic-term?academicTermId=789
  ```
- **Response:** List of Activity objects

---

### Activity Exercises

#### Get Activity Exercises by Activity
- **URL:** `GET /activity-exercises/by-activity?activityId={id}`
- **Description:** Get all exercises for a specific activity (ordered by display order)
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/activity-exercises/by-activity?activityId=101
  ```
- **Response:** List of ActivityExercise objects

#### Get Activity Exercises by Exercise
- **URL:** `GET /activity-exercises/by-exercise?exerciseId={id}`
- **Description:** Get all activities that contain a specific exercise
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/activity-exercises/by-exercise?exerciseId=202
  ```
- **Response:** List of ActivityExercise objects

#### Get Activity Exercise by Activity and Exercise
- **URL:** `GET /activity-exercises/by-activity-and-exercise?activityId={id}&exerciseId={id}`
- **Description:** Get the specific activity-exercise relationship
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/activity-exercises/by-activity-and-exercise?activityId=101&exerciseId=202
  ```
- **Response:** Single ActivityExercise object

---

### Enrollments

#### Get Enrollments by Academic Term
- **URL:** `GET /enrollments/by-academic-term?academicTermId={id}`
- **Description:** Get all enrollments for a specific academic term
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/enrollments/by-academic-term?academicTermId=789
  ```
- **Response:** List of Enrollment objects

#### Get Enrollments by Student
- **URL:** `GET /enrollments/by-student?studentId={id}`
- **Description:** Get all enrollments for a specific student
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/enrollments/by-student?studentId=303
  ```
- **Response:** List of Enrollment objects

#### Get Enrollments by Group Section
- **URL:** `GET /enrollments/by-group-section?groupSectionId={id}`
- **Description:** Get all enrollments for a specific group section
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/enrollments/by-group-section?groupSectionId=123
  ```
- **Response:** List of Enrollment objects

---

### Exercises

#### Get Exercises by Difficulty
- **URL:** `GET /exercises/by-difficulty?difficulty={level}`
- **Description:** Get all exercises with a specific difficulty level
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/exercises/by-difficulty?difficulty=3
  ```
- **Response:** List of Exercise objects

#### Get Exercises by Target Profile
- **URL:** `GET /exercises/by-target-profile?profileCode={code}`
- **Description:** Get all exercises for a specific target profile
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/exercises/by-target-profile?profileCode=BEGINNER
  ```
- **Response:** List of Exercise objects

---

### Exercise Target Profiles

#### Get Exercise Target Profiles by Profile
- **URL:** `GET /exercise-target-profiles/by-profile?profileCode={code}`
- **Description:** Get all exercise-profile relationships for a specific profile
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/exercise-target-profiles/by-profile?profileCode=INTERMEDIATE
  ```
- **Response:** List of ExerciseTargetProfile objects

#### Get Exercise Target Profiles by Exercise
- **URL:** `GET /exercise-target-profiles/by-exercise?exerciseId={id}`
- **Description:** Get all target profiles for a specific exercise
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/exercise-target-profiles/by-exercise?exerciseId=404
  ```
- **Response:** List of ExerciseTargetProfile objects

---

### Group Sections

#### Get Group Sections by Academic Term
- **URL:** `GET /group-sections/by-academic-term?academicTermId={id}`
- **Description:** Get all group sections for a specific academic term
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/group-sections/by-academic-term?academicTermId=789
  ```
- **Response:** List of GroupSection objects

#### Get Group Section by Academic Term and Code
- **URL:** `GET /group-sections/by-academic-term-and-code?academicTermId={id}&groupCode={code}`
- **Description:** Get a specific group section by academic term and group code
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/group-sections/by-academic-term-and-code?academicTermId=789&groupCode=A01
  ```
- **Response:** Single GroupSection object

---

### Level Tiers

#### Get Level Tiers by Academic Term
- **URL:** `GET /level-tiers/by-academic-term?academicTermId={id}`
- **Description:** Get all level tiers associated with a specific academic term
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/level-tiers/by-academic-term?academicTermId=789
  ```
- **Response:** List of LevelTier objects

---

### Performance Tier Histories

#### Get Performance Tier Histories by Student
- **URL:** `GET /performance-tier-histories/by-student?studentId={id}`
- **Description:** Get all performance tier histories for a student (ordered by computed date, newest first)
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/performance-tier-histories/by-student?studentId=303
  ```
- **Response:** List of PerformanceTierHistory objects

#### Get Performance Tier Histories by Academic Term
- **URL:** `GET /performance-tier-histories/by-academic-term?academicTermId={id}`
- **Description:** Get all performance tier histories for a specific academic term
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/performance-tier-histories/by-academic-term?academicTermId=789
  ```
- **Response:** List of PerformanceTierHistory objects

#### Get Latest Performance Tier History by Student and Academic Term
- **URL:** `GET /performance-tier-histories/latest-by-student-and-academic-term?studentId={id}&academicTermId={id}`
- **Description:** Get the most recent performance tier history for a student in a specific academic term
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/performance-tier-histories/latest-by-student-and-academic-term?studentId=303&academicTermId=789
  ```
- **Response:** Single PerformanceTierHistory object

---

### Permissions

#### Get Permission by Name
- **URL:** `GET /permissions/by-name/{name}`
- **Description:** Retrieve a specific permission by its name
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/permissions/by-name/CREATE_ACTIVITY
  ```
- **Response:** Single Permission object

#### Get Permissions by Role
- **URL:** `GET /permissions/by-role?roleId={id}`
- **Description:** Get all permissions assigned to a specific role
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/permissions/by-role?roleId=505
  ```
- **Response:** List of Permission objects

---

### Roles

#### Get Role by Name
- **URL:** `GET /roles/by-name/{name}`
- **Description:** Retrieve a specific role by its name
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/roles/by-name/PROFESSOR
  ```
- **Response:** Single Role object

#### Get Roles by Permission
- **URL:** `GET /roles/by-permission?permissionName={name}`
- **Description:** Get all roles that have a specific permission
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/roles/by-permission?permissionName=VIEW_GRADES
  ```
- **Response:** List of Role objects

---

### Scoring Events

#### Get Scoring Events by Student
- **URL:** `GET /scoring-events/by-student?studentId={id}`
- **Description:** Get all scoring events for a specific student
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/scoring-events/by-student?studentId=303
  ```
- **Response:** List of ScoringEvent objects

#### Get Scoring Events by Awarded By
- **URL:** `GET /scoring-events/by-awarded-by?awardedById={id}`
- **Description:** Get all scoring events awarded by a specific user
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/scoring-events/by-awarded-by?awardedById=606
  ```
- **Response:** List of ScoringEvent objects

#### Get Scoring Events by Activity
- **URL:** `GET /scoring-events/by-activity?activityId={id}`
- **Description:** Get all scoring events for a specific activity
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/scoring-events/by-activity?activityId=101
  ```
- **Response:** List of ScoringEvent objects

---

### Teaching Assignments

#### Get Teaching Assignments by Professor
- **URL:** `GET /teaching-assignments/by-professor?professorId={id}`
- **Description:** Get all teaching assignments for a specific professor
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/teaching-assignments/by-professor?professorId=707
  ```
- **Response:** List of TeachingAssignment objects

#### Get Teaching Assignments by Group Section
- **URL:** `GET /teaching-assignments/by-group-section?groupSectionId={id}`
- **Description:** Get all teaching assignments for a specific group section
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/teaching-assignments/by-group-section?groupSectionId=123
  ```
- **Response:** List of TeachingAssignment objects

---

### Users

#### Get User by Email
- **URL:** `GET /users/by-email/{institutionalEmail}`
- **Description:** Retrieve a specific user by their institutional email
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/users/by-email/juan.perez@icesi.edu.co
  ```
- **Response:** Single UserAccount object

#### Get Users by Role
- **URL:** `GET /users/by-role?roleName={name}`
- **Description:** Get all users with a specific role
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/users/by-role?roleName=STUDENT
  ```
- **Response:** List of UserAccount objects

#### Get Users by Self Declared Level
- **URL:** `GET /users/by-self-declared-level?levelCode={code}`
- **Description:** Get all users with a specific self-declared level
- **Example:**
  ```
  http://192.168.131.107:8089/pcomputacional/users/by-self-declared-level?levelCode=ADVANCED
  ```
- **Response:** List of UserAccount objects

---

## Database Access

- **H2 Console URL:** `http://192.168.131.107:8089/pcomputacional/h2-console`
- **Database URL:** `jdbc:h2:mem:compu2-class`
- **Username:** `admin`
- **Password:** `admin`

## Response Formats

- **Single Entity Endpoints:** Return `ResponseEntity<T>` (200 OK if found, 404 Not Found if not found)
- **List Endpoints:** Return `List<T>` (empty list if no results)
- **Content Type:** `application/json`

## Common HTTP Status Codes

- **200 OK:** Successful request with data
- **404 Not Found:** Entity not found (for single entity endpoints)
- **400 Bad Request:** Invalid parameters
- **500 Internal Server Error:** Server error
