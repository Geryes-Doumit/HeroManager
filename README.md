# Hero Manager
[![forthebadge](https://forthebadge.com/images/badges/made-with-kotlin.svg)](https://forthebadge.com) ![using-jetpack-compose](https://github.com/user-attachments/assets/cf2f9a31-b9cb-43f8-9f9c-f3a957b2af61) [![forthebadge](https://forthebadge.com/images/badges/powered-by-coffee.svg)](https://forthebadge.com)

Hero Manager is an android app created for a last-year Computer Science project.
I was asked to create an application using Romm Database, that incorporated three kinds of data, that are liked together in a way.

I came up with Hero Manager.

The three main concepts are Heroes, Teams and Missions.

## Heroes
You can list, add and edit heroes via these screens:

<table>
  <tr>
    <td> <img src="https://github.com/user-attachments/assets/38422090-c543-486e-8a9c-01d4ca00aadb"> </td>
    <td> <img src="https://github.com/user-attachments/assets/4e2454d3-c95b-40b7-b91f-e1dc39a4d35f"> </td>
    <td> <img src="https://github.com/user-attachments/assets/b2b03aff-6ebd-492c-877f-a516884c82ec"> </td>
  </tr>
</table>

A hero has a Hero name, a real name, a power level and can be attached to a team.

If said team is busy, we cannot edit or delete de hero.

## Teams
You can manage different teams via these screens:

<table>
  <tr>
    <td> <img src="https://github.com/user-attachments/assets/eb3ad442-6853-4c08-89f6-0279311b9f76"> </td>
    <td> <img src="https://github.com/user-attachments/assets/67cb296d-e9d0-4374-9d4b-a40d74447578"> </td>
    <td> <img src="https://github.com/user-attachments/assets/0a5e3f33-39f0-48c0-a2e7-caac156d77d0"> </td>
  </tr>
</table>

A team has a name, has members and a leader (that has to be a member).
The total team power is calculated by summing the power of every member.

The team has a state (available or busy) depending on if it is in an ongoin mission.

## Missions
You can deal with missions via these screens: 

<table>
  <tr>
    <td> <img src="https://github.com/user-attachments/assets/a581ae79-2800-47d9-a1d2-5e50f4694f44"> </td>
    <td> <img src="https://github.com/user-attachments/assets/29642c4b-e8a0-46ef-ba8e-35d07e49013e"> </td>
    <td> <img src="https://github.com/user-attachments/assets/efc00335-034d-4d95-9c30-435e4256d40a"> </td>
  </tr>
</table>

A mission has a name, a description, a minimum required power level, and a state.
When you create a mission, you can choose to assign a team to it.

When editing a mission, you can start it by pressing "Start Mission". This will set the mission state to "Ongoing" and set the state of the assigned team to "Busy".
You can end the mission by clicking "End Mission", which will free up the team.

A mission can only be started if:
- The assisgned team isn't already busy
- The assigned team has a sufficient power lever
- Its name and description are not empty
- Its minimum required power is greater than zero

---
<p align=center>Created with ❤️ by Geryes Doumit</p>
