# MaintenanceMonitor
Abschlussprojekt von SLM W22

Projektteilnehmer:

Ilic Sanja – ic21b110 (Github: saneey03)
Salkic Nermina – ic21b114 (Github: Norm01, nsanes)
Susaki Yukari – ic21b053 (Github: Yukari-Palfsaki)

Aufgabe:
Java - Spring Boot - REST API - Maintenance Monitor

Ein Team von maximal 3 Mitgliedern sollte eine REST-basierte Anwendung in Java implementieren (mit Spring Boot). Der Dienst sollte in der Lage sein, eine zentral gespeicherte Nachricht zu verwalten. Es sollte in der Lage sein,

Übermitteln der Nachricht an einen Client.
Setzen Sie es auf eine bestimmte Nachricht.
Zurücksetzen der Nachricht.

Requirements:
Github
Kanban Board
Continuous Integration-Pipeline
Continuous Delivery (Pipeline Artifact)

References:
/api/message                                                                                       -> "Everything works as expected"
/api/message/set?m=Service+checks:+No+power+until+5:00+pm  -> "ok"
/api/message                                                                                       -> "Service checks: No power until 5:00 pm"
/api/message/reset                                                                                 -> "ok"
/api/message                                                                                       -> "Everything works as expected"
