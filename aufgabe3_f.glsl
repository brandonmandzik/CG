#version 330

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform sampler2D textureSampler;

out vec3 color;

in vec3 FragPos;
in vec3 eckenFarben;
in vec3 normalVector;
in vec2 uvs;

 
uniform vec3 lightPos = vec3(200, 100, 100); 
vec3 lightColor = vec3(0.8f, 0.8f, 0.8f);
uniform vec3 viewPos= vec3 (0,0,0);


 



void main(){

 	float specularStrength = 0.5f;
	
	float ambientStrength = 0.3f;
    vec3 ambient = ambientStrength * lightColor;
	

	vec3 tempnormal = normalize(normalVector);
	vec3 lightDir = normalize(lightPos - FragPos);
	
	
	
	float diff = max(dot(tempnormal, lightDir), 0.0);
	vec3 diffuse = diff * lightColor;
	
	vec3 viewDir = normalize(viewPos - FragPos);

	vec3 reflectDir = reflect(-lightDir, tempnormal);

	
	float spec = pow(max(dot(viewDir, reflectDir), 0.0), 8);
	vec3 specular = specularStrength * spec * lightColor; 
	
	vec4 texel = texture(textureSampler, uvs);
	
	vec3 result = (ambient + diffuse +  specular) ;
	color = texel.xyz*result ;
	
}





