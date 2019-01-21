#version 330

layout(location=0) in vec4 eckenAusJava;

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
out vec3 eckenFarben;
out vec3 normalVector;
out vec3 FragPos;


   
        
        
void main(){
    mat4 transformationMatrix = viewMatrix * modelMatrix;

    mat3 normalMatrix = inverse(transpose(mat3(transformationMatrix)));
    normalVector = normalMatrix * eckenAusJava.xyz;
	
    FragPos = vec3(viewMatrix * modelMatrix * eckenAusJava);
	gl_Position = projectionMatrix*viewMatrix*modelMatrix*eckenAusJava;	
	
	
}
