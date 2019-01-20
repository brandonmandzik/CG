#version 330

layout(location=0) in vec4 eckenAusJava;
layout(location=1) in vec3 eckenNormalen;
uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
out vec3 eckenFarben;
out vec3 normalVector;
out vec3 FragPos;
   
        
        
void main(){
	 if (eckenNormalen.x > -0.1 && eckenNormalen.y > -0.1 && eckenNormalen.z > 0.5) {
        eckenFarben = vec3(1.0, 0.0, 0.0);
    } else if (eckenNormalen.x == 0 && eckenNormalen.y == 0 && eckenNormalen.z == -1) {
        eckenFarben = vec3(0.0, 1.0, 0.0);
    } else if (eckenNormalen.x == 0 && eckenNormalen.y == -1 && eckenNormalen.z == 0) {
        eckenFarben = vec3(0.0, 0.0, 1.0);
    } else if (eckenNormalen.x == 0 && eckenNormalen.y == 1 && eckenNormalen.z == 0) {
        eckenFarben = vec3(1.0, 0.0, 1.0);   
    } else if (eckenNormalen.x == 1 && eckenNormalen.y == 0 && eckenNormalen.z == 0) {
        eckenFarben = vec3(1.0, 1.0, 0.0); 
    } else if (eckenNormalen.x == -1 && eckenNormalen.y == 0 && eckenNormalen.z == 0) {
        eckenFarben = vec3(0.0, 1.0, 1.0); 
    } 
    
    mat4 transformationMatrix = viewMatrix * modelMatrix;

    mat3 normalMatrix = inverse(transpose(mat3(transformationMatrix)));
    normalVector = normalMatrix * eckenNormalen;
	
    FragPos = vec3(viewMatrix * modelMatrix * eckenAusJava);
	gl_Position = projectionMatrix*viewMatrix*modelMatrix*eckenAusJava;	

}