#version 330

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
out vec3 color;
vec3  eckenFarben =  vec3 (0,0.6f,0.8f);
in vec3 normalVector;
//uniform vec3 lightPos = vec3(2, 4, 1.5f);  
uniform vec3 lightPos = vec3(200, 100, 100); 
in vec3 FragPos; 
uniform vec3 viewPos= vec3 (0,0,0);



 



void main(){
 	float specularStrength = 0.5f;
	
	float ambientStrength = 0.3;
    vec3 ambient = ambientStrength * eckenFarben;
	

	vec3 tempnormal = normalize(normalVector);
	vec3 lightDir = normalize(lightPos - FragPos);
	
	
	
	float diff = max(dot(tempnormal, lightDir), 0.0);
	vec3 diffuse = diff * eckenFarben;
	
	vec3 viewDir = normalize(viewPos - FragPos);

	vec3 reflectDir = reflect(-lightDir, tempnormal);

	
	float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
	vec3 specular = specularStrength * spec * vec3(1.0,1.0,1.0); 
	

	vec3 result = ambient + diffuse+  specular;
	color = result;
	
}













