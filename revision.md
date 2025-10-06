# Revisión del modelo de datos

1. Hacen falta las entidades de roles y permisos
2. Todos los elementos de la base de datos deberían estar en Inglés
3. Falta el atributo apellido
4. Falta documento y tipo de documento del usuario, también código de estudiante
5. Existe una relación extraña entre usuario y grupo
6. En la relación estudiante_grupo, falta la fecha en la que el usuario fue asignado a dicho grupo
7. En la relación estudiante_grupo, debería específicar cuál es el tipo de relación, profesor o estudiante. Por ende debería de renombrarse.
8. En un curso pueden existir multiples profesores asignados, por ende en la relación estudiante_grupo debería de estar relacionado.
9. La relación actividad_grupo obliga que si tengo la misma actividad y quiero especificarla a diferentes grupos, tenga que copiar toda su información en el nuevo grupo. Por ende se recomienda crear una tabla intermedia para relacionar que una actividad puede estar relacionada a muchos grupos y un grupo puede tener relación a muchas actividades.
10. Dificultad debería ser una entidad aparte.
11. En la entidad resolucion puede que exista una redundancia entre ejercicio y actividad, quizá debería de borrarse la relación entre resolución y actividad.
